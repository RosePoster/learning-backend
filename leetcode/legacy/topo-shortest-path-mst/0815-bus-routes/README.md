
# 815. Bus Routes

## 我的思考
本题经历了三版实现。

第一版将每条公交线路视为一个图节点。如果两条线路存在共同站点，则在线路之间建边；随后在 route graph 上做 BFS。为了判断线路是否相交，先为每条 route 建立 `HashSet`，然后在读取当前线路站点时，与此前所有线路比较是否包含相同站点。这版建模方向正确，但显式建 route graph 的过程较重。

第二版使用 `nodeToRoutes` 建立反向索引：每个站点映射到经过该站点的所有 route。随后对同一站点下的所有 route 两两连边，得到 route graph。这比第一版更清晰，也避免了逐 route set 比较，但仍然会显式生成大量 route-route 边。

第三版不再显式建立 route graph，而是在 BFS 过程中动态展开邻居。仍然保留 `nodeToRoutes` 作为反向索引：当前坐上某条 route 后，可以到达该 route 覆盖的所有站点；到达某个站点后，可以换乘所有经过该站点的 route。这样 route 是 BFS 状态，站点是换乘时的中间索引。

第三版使用两个 visited：`visitedRoute` 防止同一条公交线路重复入队，`visitedNode` 防止同一个站点反复展开 `nodeToRoutes`。初始队列放入所有包含 `source` 的 route，`ans = 1` 表示已经坐上一辆公交；如果当前 route 覆盖 `target`，则直接返回当前 `ans`。

三版耗时分别约为：第一版 0-30min，第二版 30-45min，第三版 45-75min。整体演进是：显式 route graph → 用站点反向索引建图 → 不显式建图，BFS 时动态生成邻居。

## 卡点
- 第一版直接显式建 route graph，建图过程较重，需要反复判断 route 之间是否共享站点。
- 第二版虽然使用 `nodeToRoutes` 优化了建图，但仍然对同一站点下的 routes 两两连边，可能产生大量显式边。
- 第三版才收敛到按需生成邻居：不提前建立 route-route graph，而是在 BFS 时通过站点反向索引动态找到可换乘 route。
- 本题主要耗时在图建模逐步压缩上，不是 BFS 本身。

## 关键点
- 线路 route 可以作为 BFS 节点；坐上一条线路后，公交数量加一。
- 站点 stop 更适合作为 route 之间的反向索引，而不是直接作为最终 BFS 层的答案状态。
- `nodeToRoutes` 表示某个站点能换乘到哪些 route，是本题的核心索引结构。
- `visitedRoute` 和 `visitedNode` 都需要维护：前者防止 route 重复入队，后者防止同一站点重复展开导致复杂度膨胀。
- 如果原始输入不适合直接 BFS，先建立反向索引；如果显式建边太重，则考虑在 BFS 过程中按需生成邻居。

## 主流解对照
LeetCode 主流做法通常也是建立 `stop -> routes` 的反向索引，并从所有包含 `source` 的 route 或 stop 开始做 BFS，动态通过站点扩展可换乘线路，避免显式构建完整 route graph。当前第三版与主流解算法等价，属于从显式建图逐步收敛到按需邻居生成的实现。

## 复杂度
- 设 `R` 为 route 数量，`S` 为所有 routes 中站点出现次数总和。
- 第一版 / 第二版显式建 route graph 时，最坏会受到同一站点下 routes 两两连边影响，可能接近 `O(Σ k_stop²)`。
- 第三版建 `nodeToRoutes` 为 `O(S)`；每条 route 最多入队一次，每个站点最多展开一次，整体时间可视为 `O(S)`。
- 第三版空间复杂度为 `O(S + R)`。

## 注意
- 第三版是最终推荐写法；前两版有建模价值，但不适合作为最终 solution。
- 后续复刷时重点不是重新写显式 route graph，而是直接命中“反向索引 + BFS 动态邻居生成”。
- 类似题中应优先判断：是否真的需要显式建立所有边，还是可以通过 `Map<中间对象, List<状态>>` 在搜索过程中按需展开。