 # 专项探索 · 1334：从普通 Dijkstra 到可恢复最短路缓存

> 类型：专项探索 ｜ 范围：LC 1334（阈值距离内邻居最少的城市） ｜ 代码留档：[`1334/`](1334/)（Baseline / BuggyCache / BuggyCacheFix / SketchResumable）

## 摘要

一次围绕 Dijkstra + 结果复用 + 早停剪枝 + 缓存正确性的完整迭代。就题论题，Floyd 或普通 Dijkstra 即够；这次迭代的价值在于沉淀出一条通用 invariant：**缓存的正确性要求比当前搜索的正确性更强**。某状态在当前流程中只需证明"存在一条路径"；一旦被缓存并给下游复用，就可能需要证明"这是最短路径""这是完整前缀""这足以作为负判断"。

## 迭代链条

```text
普通 Dijkstra
→ 可达性早停
→ 计算结果复用（limGraph 原地替换为 shortcut cache）
→ 缺失条目论证（大多不影响候选）
→ 发现真正 bug：缓存精度
→ 区分 discovered reachable 与 finalized shortest
→ finalized 前缀证书
→ 区分 exact / inexact cache
→ 发现 inexact 简单入队不够
→ resumable Dijkstra cache
```

## 关键阶段

### 1. 普通版：可达性早停安全

每个城市作源点跑 Dijkstra，维护全局最优 `minCount`，当前源点已发现的可达城市数超过 `minCount` 时提前退出。`currCount++` 放在 relaxation（首次更新进阈值）处是安全的——早停只需证明"当前源点不可能更优"，某节点第一次被更新到阈值内即证明存在真实路径，距离是否最优不影响"可达"这一事实。

### 2. 缓存复用版：语义要求变强

优化思路：`visit(curr)` 结束后把 `dist[]` 写入 `limGraph[curr]`，后续源点 pop 出已访问过的节点 `i < curr` 时，直接用 `limGraph[i]` 批量展开 `curr -> i -> x`，不再入队松弛。本质是把已处理节点压缩为 shortcut。

### 3. 真正的 bug：discovered ≠ finalized

Dijkstra 性质：pop/finalized 的距离是最终最短路；仅被 relax/discovered 的 `dist` 只是上界。若 `visit(j)` 因 discovered count 早停，`dist[]` 混入 tentative 距离后写进缓存，下游用它做 shortcut 时可能高估 `dist(curr→j) + cachedDist(j→k)`，把真实在阈值内的 `k` 错误排除，`currCount` 被低估，最终误选答案。

```text
discovered reachable 只能证明可达；
finalized shortest 才能作为最短路缓存复用。
能证明可达 ≠ 能证明不可达 / 无需继续传播。
```

普通版与复用版中 `currCount` 的语义不同：前者是 discovered reachable counter，后者若用于缓存早停则必须接近 shortest-prefix certificate counter。

### 4. 修复一：计数移到 pop 后（前缀证书）

`currCount++` 从 relaxation 移到 pop/finalize，早停含义从"发现 minCount+1 个可达节点"变为"确定 minCount+1 个最短距离节点"。结合 `minCount` 单调不增，早停缓存天然包含未来所需的精确前缀——缓存不必完整，但需有足够长的精确最短路前缀。代价是早停变保守，复用收益未必抵得过维护成本，引出下一步。

### 5. 修复二：exact / inexact cache 分级

缓存条目带精度标记：exact（已 finalize，精确最短路）走快路径批量更新且不入队；inexact（tentative 上界）可作"可达"的正证据，不能作"不可达"的负证据或 shortcut 终止传播。即 fast path + lazy repair：平时走快路径，缓存语义不足以保证正确性时退回慢路径。

单纯把 inexact 节点入队仍不够：inexact 值本身可能已高估，真正缺失的是 `visit(j)` 早停时留在优先队列里的那段未完成搜索。

### 6. 终点：可恢复 Dijkstra 缓存

缓存从静态结果表升级为可恢复搜索状态：

```text
cache[j] = { distFromJ[], finalized[], pqSnapshot, finalizedCount }
```

下游经 `j` 复用时，可用预算 `budget = threshold - dist(curr, j)`，调用 `resumeDijkstra(j, budget)` 把 `j` 的缓存按需补全到 `pq.peek().dist > budget` 为止——依据 Dijkstra 不变量，其余未 finalize 节点的真实最短距离必大于 budget，对当前源点无贡献。语义即"把 shortest-path cache 补全到 budget 半径"。

## 与系统工程的对应

fast path / slow path、speculative execution、lazy repair、dirty bit、deoptimization、cache coherence——共同模式：

```text
为了快，先保存一个不完整但有用的中间状态；
平时走 fast path；发现该状态不足以保证正确性时，按需补全、回退或修复。
```

需要区分的状态强度谱系：可用 / 可信 / 可复用 / 可作正证据 / 可作负证据。

## 最终 invariant

```text
找到一条路径 ≠ 找到最短路径
能证明可达 ≠ 能证明不可达
当前流程够用的中间状态 ≠ 可安全缓存给下游复用的权威状态
```

许多工程 bug 的本质相同：一个模块内部暂时够用的中间状态，被另一个模块当作更强语义的状态复用。

## 关联

- [Round 1 · Union-Find](round-01-union-find.md)：镜像命题——弱原语被当强工具使用。
- [Round 4 · 审计](round-04-audit-r1-r12.md)：对偶命题——0424 弱状态够用却维护强状态（过剩同样有代价）。
- 复刷标准：不必实现可恢复缓存，回到普通 Dijkstra 即可，但应能在 30 秒内复述 "discovered ≠ finalized"。
