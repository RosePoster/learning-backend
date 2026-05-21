# 1203. Sort Items by Groups Respecting Dependencies

## 我的思考
本题最开始采用直接建模：不先把 `-1` item 归一化成新的 group，而是保留题目的原始语义，直接构造一个在线调度模型。

核心设计是：

- `taskDegree[i]` 表示 item `i` 还未完成的前置 item 数量；
- `groupExternalDegree[g]` 表示 group `g` 尚未完成的外部依赖边数量；
- `support[item]` 表示当前 item 完成后，可以支持哪些后继 item；
- `readyGroup` 存放外部依赖已清零、可以被激活的 group；
- `readyNoGroupTask` 存放无组且依赖已满足的 item；
- `activeQueue` 用于处理当前被激活的 group，保证同组 item 连续输出。

这版解法本质是“在线双层拓扑调度”：item 层真实推进依赖，group 层只负责外部依赖门禁。一个 group 只有在 `groupExternalDegree == 0` 时才能被激活；激活后只处理该 group 内部 ready 的 item，从而保证同组连续。无组 item 本来不需要连续性，因此单独作为自由任务流处理。

这个建模贴近题目语义，也符合自己的直接建模习惯。但本题复杂度较高，直接建模虽然起步快，后续状态推导和实现收敛很慢。总耗时约 90min，主要深陷在状态推导和调度细节中。

中途才想到主流做法：给每一个 `-1` item 分配一个独立 group，将所有 item 统一纳入 group 体系，再做 group 层拓扑和 item 层拓扑。这个抽象归一化步骤一开始没有立即想到。如果在约 40min 时果断转向二重调度，可能整体 60min 左右可以完成。

本题暴露出一个明显问题：复杂问题中，直接建模往往能迅速启动，但状态数量容易膨胀，导致实现和证明成本变高；而更高维的抽象建模虽然更统一、更便宜，但自己通常会慢一拍才想到。

## 卡点
- 直接建模后，调度状态较多：`readyGroup`、`readyNoGroupTask`、`activeQueue`、`groupExternalDegree`、`taskDegree` 同时参与推进，状态推导成本很高。
- 一开始没有及时想到“给每个 `-1` item 分配独立 group”这个归一化技巧，导致一直在原始题意中手搓调度器。
- `groupExternalDegree` 的语义需要非常清楚：它不是 group 内 item 数量，而是该 group 尚未完成的外部依赖边数量。
- 建图阶段变量命名容易混乱。对依赖 `item -> i` 而言，更清楚的命名应是 `prerequisiteGroup` 和 `targetGroup`，而不是泛泛的 `currGroup / nextGroup`。
- 当前解法的正确性依赖多个不变量，推导和解释成本明显高于主流二重拓扑。

## 关键点
- 本题有两层约束：
  1. item 之间的前置依赖必须满足；
  2. 同一 group 的 item 在最终结果中必须连续。
- 当前解法采用在线调度：
  - item 层用 `taskDegree` 维护完整依赖；
  - group 层用 `groupExternalDegree` 控制 group 何时可被激活；
  - group 被激活后，通过 `activeQueue` 连续处理该 group 内部 ready item；
  - 无组 item 不需要连续性，单独进入 `readyNoGroupTask`。
- 主流解法更抽象：给每个 `-1` item 分配独立 group，然后做 group 层拓扑和 item 层拓扑。这样可以减少特殊分支，让证明和实现更标准。
- 直接建模能更贴近题目语义；但当调度状态开始膨胀时，应及时检查是否存在更高维的归一化建模。
- 对复杂题，不能只比较“第一思路启动速度”，还要比较后续实现成本、证明成本和 debug 成本。

## 复杂度
- 时间：`O(n + m + E)`，其中 `n` 为 item 数量，`m` 为 group 数量，`E` 为依赖边数量。
- 空间：`O(n + m + E)`。

## 注意
- 总耗时过长，并且暴露了“复杂问题中直接建模状态膨胀”的问题。
- 复刷时建议做两件事：
  1. 对照当前在线调度解法，重新梳理 `taskDegree / groupExternalDegree / activeQueue` 的不变量；
  2. 再写一遍主流二重拓扑版本，重点体会“给 `-1` item 分配独立 group”如何降低实现复杂度。
- 以后遇到复杂约束题，如果直接建模超过一定时间仍在增加状态变量，应主动暂停，思考是否可以通过抽象归一化减少特殊情况。