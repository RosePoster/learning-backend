

# 1334. 阈值距离内邻居最少的城市：从普通 Dijkstra 到可恢复最短路缓存

这次思考不是普通题解，而是一次围绕 `Dijkstra + 复用计算结果 + 早停剪枝 + cache correctness` 的完整迭代。

一开始并没有第一时间命中 Dijkstra。备考 408 时学过 Dijkstra，但当时只要求掌握思想，没有真正手写过。看到本题时，首先想到的是能不能复用已经算过的结果：如果某个节点已经被访问过，那么它的边表里是否可以记录“从该节点出发，在阈值内能到达的所有节点及其距离”，后续其他节点经过它时就不用重新搜索。

最初想过递归式复用：记录每个节点是否访问过，访问过意味着该节点的邻接表已经被改造成“阈值内可达节点 + 对应距离”的缓存，然后递归复用。但这个方向很快遇到问题：图中节点之间可能互相可达、互相依赖，递归会陷入复杂的环形依赖。

随后想到 Dijkstra。先实现了普通 Dijkstra，又踩了一个细节：PriorityQueue 里不能只比较外部 `dist[]`，因为 `dist` 更新不会自动调整队列顺序。正确做法是把 `{node, distance}` 作为状态放入队列，比较状态里的 distance，并在 pop 时跳过过期状态或已访问节点。

## 第一阶段：普通 Dijkstra + 可达性早停

普通版本中，每个城市作为源点跑一次 Dijkstra，统计阈值内可达城市数量。为了避免无意义搜索，维护当前最优的 `minCount`，当当前源点已经发现的可达城市数超过 `minCount` 时，直接退出。

这里 `currCount++` 放在 relaxation 后，也就是某个节点第一次被更新为 `dist[j] <= threshold` 时：

```java
if (dist[j] == distanceThreshold + 1) currCount++;
dist[j] = w;
pq.offer(new int[]{j, w});
```

这个语义在普通 Dijkstra 中是成立的。原因是：此时只需要判断当前源点是否已经不可能成为答案。只要某个节点第一次被更新到阈值内，就说明存在一条真实路径到达它。即使这个距离不是最终最短路，也不影响“它已经可达”这个事实。

因此普通版的早停依据是：

```text
已经发现 minCount + 1 个真实可达节点
=> 当前源点不可能比当前答案更优
=> 可以提前停止
```

这个剪枝是安全的。

## 第二阶段：把 limGraph 从邻接表改造成最短路缓存

之后开始尝试复用计算结果。核心想法是：

> 每轮 `visit(curr)` 结束后，根据得到的 `dist[]` 更新 `limGraph[curr]`，让 `limGraph[curr]` 不再表示原始邻接边，而是表示“从 curr 出发，阈值内能到达的所有城市及对应距离”。

外层遍历顺序是 `0..n-1`，所以在访问 `curr` 时，所有 `j < curr` 的节点都已经访问过，其 `limGraph[j]` 已经被替换为缓存。

于是得到一个优化分支：

```java
if (i < curr) {
    for (int[] cached : limGraph[i]) {
        int j = cached[0];
        int w = d + cached[1];

        if (w > distanceThreshold || w >= dist[j]) continue;

        if (dist[j] == distanceThreshold + 1) currCount++;
        dist[j] = w;
    }
    continue;
}
```

语义是：当前 Dijkstra 从 `curr` 出发，pop 出节点 `i`。如果 `i < curr`，说明 `i` 已经作为源点跑过，`limGraph[i]` 里存着从 `i` 出发的阈值内最短路结果。那么 `curr -> i -> x` 这批候选路径可以一次性通过缓存展开，不需要再把 `x` 放进优先队列。

当时的关键直觉是：

> 从 curr 到 i，再到 x 的所有最优状态，已经在 visit(i) 的过程中存储在 limGraph[i] 中了。第一步用 limGraph[i] 更新 dist 时，已经享用了 i 的所有最优状态，所以不需要继续入队产生松弛。

这个想法很漂亮，本质上是把已处理节点压缩成 shortcut。

## 第三阶段：第一次正确性论证 —— 缺失条目问题

引入早停后，出现第一个正确性问题：如果 `visit(j)` 没跑完整，那么 `limGraph[j]` 并不完整，后续复用时会不会漏掉节点？

最开始的分析把问题拆成两类：

### Case A：curr 只能用上 limGraph[j] 的一部分

如果 `curr -> j` 已经消耗了一部分距离预算，那么 `curr` 只能使用 `limGraph[j]` 中距离较小的一部分条目。那些没有被 `visit(j)` 计算出来的条目距离更远，即使存在，对 `curr` 来说也更可能超过阈值。

这类缺失条目通常不会影响当前 `curr` 的可达性判断。

### Case B：curr 能用上 limGraph[j] 的全部条目

如果 `curr` 能用上 `limGraph[j]` 里的全部条目，而 `visit(j)` 当初是因为 `currCount(j) > minCount` 早停，那么 `limGraph[j]` 已经至少包含 `minCount + 1` 个不同节点。此时 `curr` 通过 `j` 至少也能到达 `minCount + 1` 个节点，必然不是更优候选。

所以“缺失条目”这一侧看起来可以解释：

```text
如果 curr 用不上缺失部分，则缺失不影响；
如果 curr 能用上足够多的已有部分，则 curr 已经不是候选。
```

这一段论证后来被证明方向基本是对的，但它只覆盖了一半问题。

## 第四阶段：Opus 找到的 bug —— discovered reachable 不等于 finalized shortest

真正的问题不是“缺失条目”，而是“缓存精度”。

原版本中，`currCount++` 发生在 relaxation 时。也就是说，一个节点只要第一次被更新进 `dist[]`，就会被计数。对于普通 Dijkstra，这没问题，因为它只证明“当前源点已经能到这个节点”。

但对于缓存复用版本，问题变了：

> `visit(j)` 早停后，会把 `dist[]` 写入 `limGraph[j]`，供后续源点当作 shortest path shortcut 使用。

Dijkstra 的性质是：

```text
pop/finalized 的节点距离一定是最终最短路；
只被 relax/discovered 但还没 pop 的节点，dist 只是一个上界，可能不是最短路。
```

因此如果 `visit(j)` 因为 discovered count 超过 `minCount` 而早停，`dist[]` 中可能混入 tentative 距离。这些距离代表真实路径，但不一定是最短路径。

后续 `visit(curr)` 使用 `limGraph[j]` 时，如果某个 tentative 距离被高估，就可能出现：

```text
真实 dist(curr -> j -> k) <= threshold
但估算 dist(curr -> j) + cachedDist(j -> k) > threshold
```

于是 `k` 被错误排除，`currCount` 被低估。若真实 `currCount > minCount`，但估算值 `<= minCount`，就会把一个本不该成为答案的城市误选出来。

这个 bug 很隐蔽，因为它不在“有没有路径”层面，而在“缓存里的距离能不能作为最短路证据”层面。

关键语义错位是：

```text
discovered reachable 只能证明可达；
finalized shortest 才能作为最短路缓存复用。
```

这也是这次思考中最重要的分界。

## 第五阶段：为什么普通版可以 discovered 时计数，而复用版不行

普通版中，`currCount++` 放在第一次更新 `dist[j]` 时是安全的。

因为普通版只关心当前源点是否已经不可能成为答案。发现一条到 `j` 的路径，就足以证明 `j` 可达。距离是否最优不重要。

复用版不同。复用版不仅要判断当前源点的 count，还会把 `dist[]` 写入缓存，供未来节点使用。缓存一旦被当作 shortcut，就要求更强语义：距离必须是精确最短路，至少在需要复用的前缀范围内必须精确。

所以两种版本中 `currCount` 的语义不同：

```text
普通版 currCount：discovered reachable counter
复用版 currCount：如果要用于 cache 早停，必须接近 shortest-prefix certificate counter
```

这就是原 bug 的根源。

## 第六阶段：第一种修复思路 —— currCount 移到 pop 后

最直接的修复是把 `currCount++` 从 relaxation 阶段移动到 pop/finalize 阶段。

也就是：

```java
if (visited[i]) continue;
visited[i] = true;
if (i != curr) currCount++;
```

这样早停含义从：

```text
已经发现 minCount + 1 个可达节点
```

变成：

```text
已经确定 minCount + 1 个最短距离节点
```

此时 `visit(j)` 即使早停，也至少缓存了 `minCount + 1` 个精确的最短距离前缀。

结合 `minCount` 单调不增：

```text
oldMinCount >= currentMinCount
```

过去缓存下来的 `oldMinCount + 1` 个 finalized 节点，天然覆盖未来所需的 `currentMinCount + 1` 个节点。

于是新的正确性直觉是：

```text
如果 curr 通过 j 能够触达前 currentMinCount + 1 个精确最短距离节点，curr 必然不是候选；
如果触达不了第 currentMinCount + 1 个精确节点，那么更远节点也不可能通过 j 落入阈值。
```

这说明：`limGraph[j]` 不必完整，但需要有足够多的精确最短距离前缀。

这一版思路把算法从“不完整缓存”修正为“前缀证书缓存”。

但这里出现了新的 trade-off：此时的代码真的比“强剪枝、不复用”的普通 Dijkstra 初始版本效率高吗？未必。

普通版可以在 relaxation 第一次发现可达节点时就 `currCount++`，因此早停更激进，while 可能更早退出；而复用版如果为了保证缓存精度，把计数移动到 pop/finalize 阶段，就会牺牲一部分早停能力。复用版虽然减少了后续优先队列的搜索深度，但也增加了 cache 展开、缓存维护和语义判断的成本。

所以问题从“这个优化是否正确”进一步变成：

```text
能否保留普通版 discovered-count 强剪枝的速度，
同时避免把 tentative 距离错误地当作 shortest-path cache 复用？
```

这个问题引出了后续对 exact cache 与 inexact cache 的区分。

## 第七阶段：进一步思考 —— exact cache 与 inexact cache

继续分析后发现，也可以不完全放弃 discovered 计数的激进早停，而是区分缓存条目的精确性。

每个缓存条目可以带一个标记：

```text
exact = true：目标节点已经 pop/finalized，距离是精确最短路
exact = false：目标节点只是 discovered/tentative，距离是某条真实路径的上界
```

这样可以保留两个事实：

```text
inexact 距离可以证明“存在一条路径”，因此用于可达性正证据是安全的；
inexact 距离不能证明“没有更短路径”，因此不能用于负判断或 shortcut 终止传播。
```

这句话非常关键：

> tentative 可以证明“可达”，但不能证明“不可达”或“无需继续传播”。

于是得到一种 lazy repair 思路：

```text
exact cache：走快路径，批量更新后不入 pq；
inexact cache：不能作为最终 shortcut，必须触发 fallback 或继续搜索。
```

这相当于 speculative optimization：平时走快路径，一旦发现缓存语义不足以保证正确性，就退回慢路径修复。

## 第八阶段：为什么“把 inexact 节点直接加入 pq”还不够

最初想到的 fallback 是：如果遇到 inexact cache entry，就把这个节点加入当前优先队列，让本轮 Dijkstra 正常尝试松弛。

但继续推敲后发现这还不够。

因为 inexact entry 的当前值本身可能已经是高估值。如果：

```text
d + cachedDist > threshold
```

直接把这个高估值入队没有意义。真正更短的路径可能藏在 `j` 到这个节点之间的某条中间路径里：

```text
j -> m -> ... -> k
```

也就是说，问题不是“k 是否要入队”，而是：

> 需要找出 j 的 Dijkstra 当初没完成的那段搜索，继续把可能缩短 k 的路径算出来。

这时才意识到，bug case 中真正缺失的不是某个节点本身，而是上一次 `visit(j)` 早停时留在优先队列里的搜索过程。

## 第九阶段：最终方向 —— 可恢复 Dijkstra 缓存

最精确的修复方向是：不要重跑 `j` 的 Dijkstra，也不要简单把 tentative 节点入队，而是保存 `j` 上次中断时的 Dijkstra 状态，在需要时继续运行。

缓存从静态结果表升级为可恢复搜索状态：

```text
cache[j] = {
    distFromJ[],
    finalized[],
    pqSnapshot,
    finalizedCount / sortedFinalizedList
}
```

当后续 `visit(curr)` 通过 `j` 使用缓存时，当前可用预算是：

```text
budget = distanceThreshold - dist(curr, j)
```

真正需要知道的是：

```text
从 j 出发，所有真实最短距离 <= budget 的节点
```

所以不必把 `j` 的 Dijkstra 跑完整，只需要继续跑到：

```text
pq.peek().dist > budget
```

此时根据 Dijkstra 不变量，剩下未 finalized 节点的真实最短距离都大于 `budget`，对当前 `curr` 没贡献。

这可以抽象成：

```java
resumeDijkstra(j, budget);
```

语义是：

```text
把 j 的 shortest-path cache 补全到 budget 半径为止。
```

这就是按需补全半径的可恢复 Dijkstra 缓存。

## 第十阶段：这个设计和底层系统优化的相似性

这次思考最后自然联想到很多底层系统优化：

```text
fast path / slow path
speculative execution
lazy evaluation
lazy repair
dirty bit / valid bit
deoptimization
cache coherence
amortized analysis
```

共同模式是：

```text
为了快，先保存一个不完整但有用的中间状态；
平时走 fast path；
当发现该状态不足以保证正确性时，再按需补全、回退或修复。
```

这里的 shortest path cache 也是一样：

```text
exact cache：fast path
inexact / insufficient cache：触发 resume 或 fallback
```

更重要的是，开始区分不同强度的状态：

```text
可用状态
可信状态
可复用状态
可作为正证据的状态
可作为负证据的状态
```

本题最关键的语义分界是：

```text
找到一条路径 ≠ 找到最短路径
能证明可达 ≠ 能证明不可达
当前流程够用的中间状态 ≠ 可安全缓存给下游复用的权威状态
```

这也是很多工程 bug 的本质：一个模块内部暂时够用的中间状态，被另一个模块当作更强语义的状态复用。

## 最终认识

这次从普通 Dijkstra 到最终思路，实际经历了如下链条：

```text
普通 Dijkstra
→ 可达性早停
→ 计算结果复用
→ limGraph 原地替换为 shortcut cache
→ 发现缺失条目问题
→ 论证缺失条目大多不影响候选
→ 发现真正 bug 是缓存精度
→ 区分 discovered reachable 与 finalized shortest
→ 提出 finalized 前缀证书
→ 区分 exact / inexact cache
→ 发现简单 inexact 入队不够
→ 提出 resumable Dijkstra cache
```

如果只为了 LeetCode 1334，本题用 Floyd 或普通 Dijkstra 就够了。这个优化实现复杂度已经明显超过本题。

但这次真正有价值的不是最终代码，而是 invariant：

```text
cache 的正确性要求比当前搜索的正确性更强。
```

普通搜索中，某个状态可能只需要证明“存在一条路径”；但一旦它被缓存并复用，就可能需要证明“这是最短路径”“这是完整前缀”“这足以作为负判断”。

这是这次思考里最重要的收获。