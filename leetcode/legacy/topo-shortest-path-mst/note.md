# 专题笔记：拓扑 / 最短路 / MST

本笔记是对 `topo-shortest-path-mst` 专题 12 道题的横向总结，不重复各题 README 的细节，只沉淀跨题可迁移的 pattern 与 insight。1334 的深入讨论单独放在 [`insight/1334`](../../insight/1334/README.md)。

## 一、题目分布与工具

| 子方向 | 题目 | 核心工具 |
|---|---|---|
| 拓扑排序 | 0210, 2115, 1203 | Kahn BFS + 反向依赖（`support` / 入度） |
| 拓扑变体 | 0310 (MHT), 0802 (safe states) | 同步剥叶子；DFS 三色压缩 |
| 回溯 | 0797 | DFS 内部封闭 "选-递-撤" 的写法 |
| BFS 建模 | 0815, 1368 | 按需邻居 / 反向索引；0-1 BFS = 内层吃完 0-cost closure，外层 +1 |
| 最短路 | 1334, 1514, 0778 | Dijkstra (`{node, distSnapshot}` 入队) |
| MST | 1584 | Kruskal / Prim / UnionFind（待巩固） |

## 二、跨题反复出现的主线

这一轮真正沉淀下来、不再依赖具体题目的 pattern：

### 1. 方向反转 / 反向索引

2115 的 `ingredient → recipe` `support`、0815 的 `stop → routes`、0802 的反图。原始依赖方向只表达 "我需要谁"，推进时需要的是 "我能解锁谁"。

> 看到一个图建模题，先问一句：当前方向是否适合 "已完成 → 受影响节点" 的推进？如果不适合，必须建反向索引。

### 2. 按需建图

0815 三版演进的终点是 "不显式建 route-graph，BFS 时通过反向索引动态展开"。

> 第一反应是建图时，先问一句：边数是否爆炸？能否懒展开？很多题不是 "建图 + 搜索"，而是 "在搜索过程中按需生成邻居"。

### 3. 归一化 vs 直接建模

1203 的教训。直接建模启动快，但状态会膨胀（5 个调度变量同时推进）；高维抽象（给 `-1` item 各自分配独立 group）启动慢但实现/证明成本低。

> 复杂题里要主动检查：是否值得切换到更高维的归一化建模。"第一思路启动速度" 不是唯一指标，还要看实现成本、证明成本、debug 成本。

### 4. 不变量 vs 普通遍历

0310 的关键不在 BFS，而在 "当前叶子层 = `degree == 1`" 这个不变量必须同步剥；普通 `visited` BFS 会让短分支污染候选。0802 的三色压缩也是同一类：写状态机时要显式列出每种状态的语义。

> 树/DAG 的拓扑变体题不要套普通 BFS 模板，要先写出当前层的判定不变量。

### 5. closure 内嵌外层

1368 的 0-1 BFS 抽象成 "外层按 cost 分层，内层吃完当前层 0-cost closure"。

> 这是个比 deque-BFS 更具迁移力的视角。多种代价混合的搜索题，都可以先问 "0-cost 闭包是什么，怎么一次吃完"。

## 三、1334 的核心 insight

详细推导见 [`insight/1334/README.md`](../../insight/1334/README.md)。这里只保留可迁移的结论：

> **当一个中间状态从"当前流程自用"升级为"下游复用"时，它需要满足的语义比原先更强。**

落到 Dijkstra 上：

```
discovered reachable  ≠  finalized shortest
能证明可达            ≠  能证明不可达 / 无需继续传播
relax 时计数（够用）  ≠  缓存给下一轮源点（不够用）
```

普通 Dijkstra 里 `currCount++` 放在 relaxation 是安全的——只要证明 "当前源点已经不优"。一旦把 `dist[]` 写入 `limGraph[j]` 当作 shortcut 给后续源点用，就需要 finalized 距离；否则 tentative 高估会让下游错误排除节点。

围绕这条主轴衍生出的几层升级也不是 1334 独有：

- **exact / inexact cache 区分**：tentative 可作 "可达正证据"，不能作 "不可达负证据"；
- **fast path + lazy repair**：平时走缓存，发现精度不足时再补全；
- **resumable Dijkstra**：缓存不再是结果表，而是 "上次中断时的搜索状态"，下游按当前预算 `budget = threshold - dist(curr, j)` 续跑到 `pq.peek().dist > budget` 即可。

这条对应到工程上的影子很多：dirty bit、speculative execution、cache coherence、deopt 路径。它们都在做同一件事——**为了快，先存一个不完整但有用的状态；当被证明不足时，再按需修复。**

## 四、对后续复刷的指引

- **MST（1584）** 是本专题最单薄的角，下一轮补 Kruskal / Prim / UF 的模板熟练度。
- **1203** 复刷重点不是重写在线调度，而是逼自己先用 "给 `-1` 分配独立 group" 的归一化版本，再对照直接建模看 trade-off。
- **1334** 的可恢复 Dijkstra 不必真去实现交付——它的价值在于 invariant，不在于代码。复刷时回到普通 Dijkstra 即可，但应该能在 30 秒内复述出 "discovered ≠ finalized"。
- **0815** 已经收敛到主流解，重点是模式识别速度："反向索引 + BFS 动态邻居" 应该是看到题第一反应。
- **0310** 树中心 = 层层剥叶子；标准写法用 `remain > 2` 比 `nextList.isEmpty()` 更直接，复刷时切换。
- **Dijkstra 模板**（1334 / 1514 / 0778）：`{node, distSnapshot}` 入队，pop 时跳过过期状态，pop 即 finalized。这三点应在脑中自动化。
