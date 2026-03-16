# Map System 开发笔记

## 本阶段目标
完成并跑通船舶实时状态流转主链路，明确内部统一状态模型，为后续 CPA/TCPA、风险评估与 LLM 接入打基础。

---

## 关键设计结论

### 1. 内部统一实体不要继续叫 `AisMessage`
最初系统默认只有 AIS 输入，所以内部核心类直接叫 `AisMessage`。  
但当需求变化后，输入源可能变为：

- AIS
- 本船 GPS
- 雷达
- 计算机视觉（CV）

这时继续使用 `AisMessage` 作为内部统一实体会造成语义污染。

因此，内部可信实体应升级为：

- `ShipStatus`

它表达的是**物理世界中的船舶状态**，而不是某种特定信源的消息。

---

### 2. DTO 层吸收复杂性，内部统一为 `ShipStatus`
外部不同信源的异构数据应在 mapper 层统一映射为 `ShipStatus`，例如：

- `MqttAisDto -> ShipStatus`
- `GpsDto -> ShipStatus`
- `CvDetectionDto -> ShipStatus`
- `RadarDto -> ShipStatus`

这样后续内部计算都只面向统一状态模型，不再感知具体数据来源。

核心思想：

- 外部异构
- 内部统一

---

### 3. `ShipStatus` 不需要拆成 `OwnShip` / `TargetShip`
虽然 CPA/TCPA 在逻辑上需要“本船”和“目标船”两个角色，但这不意味着要拆成两个不同类。

正确做法是：

- 保留统一类 `ShipStatus`
- 使用 `ShipRole` 区分逻辑角色

原因：

1. 两者本质上都是“船舶运动状态”
2. CPA/TCPA 依赖的是两个运动状态的相对关系，而不是两个不同类型
3. 统一结构更适合多信源融合和后续扩展

---

### 4. 本船内部 ID 固定映射为 `"ownShip"`
为了避免系统内部继续依赖 AIS 的 MMSI 语义，本船在内部统一使用固定 ID：

- `"ownShip"`

这样可以做到：

- AIS 中本船 MMSI -> `"ownShip"`
- GPS 本船数据 -> `"ownShip"`
- 其他本船传感器 -> `"ownShip"`

目标船则继续保留各自外部标识：

- AIS: MMSI
- Radar: trackId
- CV: trackId / detectionId

这样系统内部可以统一通过：

- `store.get("ownShip")`

拿到当前本船状态。

---

### 5. `confidence` 应使用 `Double`，允许 `null`
置信度字段不是必然存在的。

例如：

- AIS 可能没有显式 confidence
- GPS 可能没有 confidence
- CV 通常有 confidence

因此：

- `Double confidence` 比 `double confidence` 更合理

原因：

- `null` 表示“未知”
- `0.0` 表示“明确极低可信”

两者语义不同，不能混淆。

---

### 6. `source` 不应作为 `ShipStatus` 顶层字段
最初考虑过给 `ShipStatus` 增加：

- `source`
- `confidence`

但进一步分析后发现：

- 数据可能来自多个信源拼接
- 例如位置来自 GPS，速度来自 AIS，朝向来自 CV

此时顶层 `source` 无法准确表达来源。

因此结论是：

- `confidence` 可以保留为整体可信度
- `source` 不进入核心领域模型
- 数据来源的复杂性留在 mapper / tracker / fusion 层处理

---

## Store 层设计

### 7. 用 `ShipStateStore` 保存“当前世界状态”
系统中新增了：

- `ShipStateStore`

其职责是：

- 保存当前最新的船舶状态
- 按时间戳阻止旧消息覆盖新消息

使用结构：

- `Map<String, ShipStatus> ships`

不再额外拆为：

- `ownShip`
- `targetShips`

因为统一 map 更干净，后续编排更简单。

---

### 8. 更新时间戳比较规则应放在 Store 中
状态更新存在一个关键问题：

- 新消息不一定比旧消息新
- 网络乱序可能导致旧消息晚到

因此更新逻辑应在 `Store` 中统一处理，而不是散落在 dispatcher 或 engine 里。

规则大致为：

1. `ship == null` 或 `id == null` -> 直接失败
2. 若原状态不存在 -> 写入
3. 若原状态存在 -> 比较时间戳
4. 只允许更新更“新”的消息覆盖旧状态

这样可以保证：

- 世界状态的一致性
- 引擎不被过期数据误触发

---

### 9. `update()` 返回 `boolean` 是合理设计
`ShipStateStore.update(ship)` 不只是“写入”，它还有明确语义：

- `true`：状态确实更新成功
- `false`：输入非法或消息过旧，被忽略

因此让它返回 `boolean` 很有价值。

`Dispatcher` 可以直接据此决定是否继续后续计算：

- 若 `update()` 返回 `false`，则直接退出，不调用各引擎

这样避免旧消息引发无意义重复计算。

---

### 10. `merge` 不适合表达“是否更新成功”的语义
起初使用了 `ConcurrentHashMap.merge()` 进行时间戳条件更新，但后来发现：

- `merge` 更适合“合并结果”
- 不适合清晰表达“是否真的完成了覆盖更新”

如果想清晰拿到“是否更新成功”的语义，更合适的方式是：

- 显式写逻辑
- 或使用 `compute(...)` 并配合标记变量

结论：

- 当业务关心“最终值是什么”时，`merge` 很方便
- 当业务关心“这次有没有成功更新状态”时，`merge` 表达力不足

---

## Dispatcher 与职责划分

### 11. `Dispatcher` 是业务编排器，不是计算器
`ShipDispatcher` 的职责逐渐明确为：

1. 接收 `ShipStatus`
2. 写入 `ShipStateStore`
3. 读取当前本船 / 目标船状态
4. 调用各 engine
5. 收集结果
6. 调用 assembler 组装输出
7. 发送 WebSocket

因此它是一个典型的 orchestration 层，而不是算法层。

---

### 12. `Assembler` 只负责组装，不主动取数据
`RiskObjectAssembler` 不应该自己去 `Store` 或 engine 里找结果。

正确分工：

- engine 负责算
- dispatcher 负责调度并收集结果
- assembler 负责把已准备好的内容组装成前端 DTO

因此 CPA、风险评估、CV 预测结果都应由 dispatcher 作为参数传给 assembler，而不是 assembler 主动去拿。

---

### 13. engine 结果应使用函数局部变量，不要做成员变量
`Dispatcher` 是 Spring 单例组件，如果把：

- `cpaResult`
- `riskResult`
- `cvPredictionResult`

做成成员变量，会导致：

- 上一条消息污染下一条消息
- 并发风险
- 生命周期混乱

因此这些结果应全部作为 `dispatch()` 中的局部变量处理。

---

### 14. 先同步跑通，不要提前引入异步与结果缓存
虽然未来系统可能需要：

- 异步分发
- 线程池
- 消息队列
- `ResultStateStore`

但当前阶段主链路尚未完全稳定，因此不宜过早引入复杂机制。

当前最佳策略：

- 采用同步顺序调用
- 使用局部变量承接各结果
- 先把主链路跑通

否则会额外引入：

- 结果过期
- 结果关联
- 时序一致性
- 缓存失效

等一堆次要复杂度。

---

## Engine 层设计结论

### 15. `CpaTcpaResult` 属于 engine 层，不属于 dto 层
`CpaTcpaResult` 是算法输出，不是前后端通信对象，也不是领域基础实体。

因此应放在：

- `engine/collision`

与 `CpaTcpaEngine` 同级。

这是典型的：

- engine result object

---

### 16. CPA/TCPA 与信源无关
这是非常关键的一条结论。

CPA/TCPA 本质依赖的是：

- 本船位置
- 本船速度向量
- 目标位置
- 目标速度向量

数学上只关心相对位置和相对速度，不关心这些量来自：

- AIS
- Radar
- CV
- GPS

因此：

- CPA/TCPA 可以完全基于统一的 `ShipStatus` 实现
- 不需要为不同信源写不同版本的 CPA/TCPA

信源差异应在前面处理：

- mapper
- tracker
- filter
- fusion

而不是进入碰撞算法本身。

---

### 17. Kalman Filter / tracking 不属于 mapper
对于雷达和 CV，通常无法直接得到稳定的运动状态，往往需要：

- tracking
- 状态估计
- Kalman Filter
- 数据关联

这些过程依赖历史状态，因此不适合放在 mapper 中。

mapper 只适合：

- DTO -> 某种观测对象 / 领域对象

而状态估计系统应是独立层次。

结论：

- AIS 可直接 mapper -> `ShipStatus`
- Radar / CV 未来应先经过 tracker / fusion，再输出 `ShipStatus`

---

### 18. `RiskAssessmentEngine` 与 `ShipDomainEngine` 不应放在一起
最初 `safety` 包内包含：

- `ShipDomainEngine`
- `RiskAssessmentEngine`

后来发现这两者的职责并不属于同一层面。

`ShipDomainEngine` 只做：

- 本船安全域计算

而 `RiskAssessmentEngine` 会综合使用：

- 船舶安全域
- CPA/TCPA
- 当前距离
- 未来可能还有 CV prediction

因此 `RiskAssessmentEngine` 实际上是更高层的综合评估模块，应抽离到单独包，例如：

- `engine/risk`

而 `ShipDomainEngine` 留在：

- `engine/safety`

---

### 19. `RiskAssessmentEngine` 不应下放给前端
系统中有一个问题：

> 是否让前端根据目标船是否侵入本船安全领域来自己触发预警？

结论是不应该。

原因：

1. 风险判断属于业务逻辑，不是展示逻辑
2. 若前端自己算，容易导致前后端判定不一致
3. 多端场景下会造成重复实现和逻辑分裂

因此：

- 后端负责风险判定
- 前端只负责展示风险结果与视觉效果

---

## CPA/TCPA 实现中的关键问题

### 20. 当前 `CpaTcpaEngine` 存在的典型坑
在实现 `CpaTcpaEngine` 时暴露出几个关键问题：

#### （1）不能把同一条船同时作为 own 和 target
如果写成：

- `calculate(message, message)`

则相对位置与相对速度都变为 0，结果毫无意义。

因此 `CpaTcpaEngine` 不能消费单条消息，而应接受：

- `ownShip`
- `targetShip`

两个输入。

---

#### （2）经纬度转平面坐标时，参数顺序极易写反
要特别小心：

- `toXY(lat, lon)` 与调用顺序一致

否则数值看似能跑，实际完全错误。

---

#### （3）经纬度转平面坐标公式中，`y` 不能继续使用经度
曾出现过类似：

- `x = lon * ...`
- `y = lon * ...`

这种明显错误但不一定第一时间暴露的问题。

说明坐标转换需要单独严格核查。

---

#### （4）`COG` 转速度向量不能直接套数学坐标系公式
航海中的 `COG` 通常定义为：

- `0° = 北`
- 顺时针增加

而数学坐标系默认：

- `0° = 东`
- 逆时针增加

因此不能直接使用标准数学意义上的：

- `vx = cos(rad)`
- `vy = sin(rad)`

必须确认坐标系转换是否匹配。

---

### 21. 验证 CPA/TCPA 时，优先使用 mock 数据而不是整批 AIS 流
虽然已有 1400 条 AIS 数据，但在算法验证阶段并不合适。

原因：

- 顺序流噪声太多
- 很难构造清晰、可预期的相对运动场景
- 不利于快速判断算法是否正确

因此更适合先构造少量 mock 测试场景，例如：

1. 同向同速
2. 对向接近
3. 已经远离
4. 交叉相遇

这样更容易定位：

- 公式问题
- 坐标转换问题
- 航向角问题

---

## 配置与映射中的细节结论

### 22. `@ConfigurationProperties` 绑定要注意字段命名规则
在配置类中使用：

- `ownShipMmsi`

时，配置文件中应使用：

- `ais.own-ship-mmsi=...`

而不是：

- `ais.ownShip.mmsi=...`

后者会被解释为嵌套属性结构，无法直接绑定到单字段。

---

### 23. MMSI 校验不需要 try/catch parse int
当内部 `id` 已改为 `String` 后，MMSI 校验只需：

1. 去掉前后空格
2. 校验是否为纯数字
3. 如果要严格遵守 MMSI 规范，则要求为 9 位数字

即：

- `\\d{9}`

这样比先 parse 成整数再处理更合理，也避免把内部统一 ID 再次绑定回整数语义。

---

## 当前项目结构演化

### 24. 当前 engine 分层更合理的方向
当前结构大致演化为：

- `engine/collision`
- `engine/safety`
- `engine/risk`
- `engine/trajectoryprediction`

这种结构比最初统一堆在一个 `engine` 包里更清晰，因为它体现了算法职责的自然边界：

- 碰撞运动学
- 安全域几何
- 风险综合评估
- 轨迹预测

---

### 25. `ShipStateStore` 比“内部缓冲池”这个说法更准确
一开始曾考虑引入所谓“内部缓冲池”，后来明确这实际上不是对象池，而是：

- 当前世界状态缓存
- 状态存储

因此命名应强调它的真实职责：

- `ShipStateStore`

而不是容易误导的：

- buffer pool

---

## 提问与思考方式中值得保留的模式

### 26. 先问“算法是否依赖数据源”，这是非常值钱的问题
这个提问直接决定了架构能否解耦。

如果不先问这一步，很容易走到：

- AIS 写一套算法
- Radar 写一套算法
- CV 再写一套算法

最终系统爆炸。

这个问题的价值在于：

- 它不是实现细节问题
- 而是决定系统抽象边界的结构问题

---

### 27. 先问“顺序控制应该放在哪一层”，说明开始有分层意识
对消息时间戳覆盖逻辑的追问，本质是在问：

- 这是业务规则？
- 还是状态存储规则？

最后结论是放在 `Store` 层，这说明正在逐渐形成：

- mapper
- store
- dispatcher
- engine
- assembler

之间的职责边界意识。

这是非常重要的工程成长信号。

---

### 28. 主动压制过早异步、过早缓存、过早前端优化，是正确决策
开发过程中多次出现：

- 要不要异步
- 要不要结果缓存
- 要不要去补前端逻辑
- 要不要提前接入 LLM

最后的判断都趋于一致：

- 先把主链路跑通
- 先验证核心算法
- 先完成阶段性 issue 闭环
- 复杂机制后置

这属于很正确的工程推进策略。

---

## 阶段性推进策略总结

### 当前阶段正确优先级
1. 验证 `CPA/TCPA` 计算是否正确
2. 关闭实时流链路相关 issue
3. 再开启 LLM 接入

理由：

- 前端当前能跑，且不是当前主瓶颈
- `CPA/TCPA` 是后续风险评估和 LLM 解读的基础
- 若基础数值错误，后面所有模块都会建立在错误结果上

---

## 当前系统主线

当前项目最核心的一条主线应是：

```text
多信源输入
-> mapper 归一化
-> ShipStatus
-> ShipStateStore
-> CpaTcpa / ShipDomain / CVPrediction / RiskAssessment
-> RiskObjectAssembler
-> WebSocket
-> Frontend