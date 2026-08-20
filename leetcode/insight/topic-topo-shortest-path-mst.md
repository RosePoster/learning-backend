# 专题 · 拓扑排序 / 最短路 / MST

> 类型：专题总结 ｜ 来源：旧 Round 14（12 题）｜ 1334 的完整推导见 [deep-dive-1334-resumable-dijkstra.md](deep-dive-1334-resumable-dijkstra.md)

## 摘要

跨题沉淀五条可迁移 pattern：方向反转、按需建图、归一化建模、不变量剥层、0-cost closure。Dijkstra 模板三要点：`{node, distSnapshot}` 入队、pop 时跳过过期状态、pop 即 finalized。

## 题目分布

| 子方向 | 题目 | 核心工具 |
|---|---|---|
| 拓扑排序 | 0210 / 2115 / 1203 | Kahn BFS + 反向依赖（support / 入度） |
| 拓扑变体 | 0310（MHT）/ 0802（safe states） | 同步剥叶子；DFS 三色压缩 |
| 回溯 | 0797 | DFS 内部封闭"选-递-撤" |
| BFS 建模 | 0815 / 1368 | 按需邻居 / 反向索引；0-1 BFS |
| 最短路 | 1334 / 1514 / 0778 | Dijkstra |
| MST | 1584 | Kruskal / Prim / UF（最薄弱角） |

## 跨题主线

### 1. 方向反转 / 反向索引

2115 的 `ingredient → recipe`、0815 的 `stop → routes`、0802 的反图。原始依赖方向表达"我需要谁"，推进时需要的是"我能解锁谁"。起手检查：当前方向是否适合"已完成 → 受影响节点"的推进；不适合则建反向索引。

### 2. 按需建图

0815 三版演进的终点：不显式建 route-graph，BFS 时通过反向索引动态展开。第一反应是建图时先问：边数是否爆炸？能否懒展开？很多题不是"建图 + 搜索"，而是"搜索过程中按需生成邻居"。

### 3. 归一化 vs 直接建模

1203 的教训：直接建模启动快但状态膨胀（5 个调度变量同时推进）；高维抽象（给 -1 item 各自分配独立 group）启动慢但实现/证明/debug 成本低。复杂题要主动检查是否值得切换到更高维的归一化建模——第一思路的启动速度不是唯一指标。

### 4. 不变量 vs 普通遍历

0310 的关键不在 BFS，而在"当前叶子层 = `degree == 1`"这一不变量必须同步剥；普通 visited BFS 会让短分支污染候选。0802 的三色压缩同类：状态机必须显式列出每种状态语义。树/DAG 拓扑变体不要套普通 BFS 模板，先写出当前层的判定不变量。

### 5. 0-cost closure 内嵌外层

1368 的 0-1 BFS 抽象为"外层按 cost 分层，内层吃完当前层 0-cost 闭包"。比 deque-BFS 更具迁移力：多种代价混合的搜索题先问"0-cost 闭包是什么、怎么一次吃完"。

## 1334 的可迁移结论

> 当一个中间状态从"当前流程自用"升级为"下游复用"时，它需要满足的语义比原先更强。

```text
discovered reachable ≠ finalized shortest
能证明可达 ≠ 能证明不可达 / 无需继续传播
relax 时计数（自用够）≠ 缓存给下游（不够）
```

衍生分级：exact / inexact cache；fast path + lazy repair；resumable Dijkstra（按预算续跑）。完整推导与代码见专项文档。

## 复刷指引

- **1584（MST）**：本专题最薄弱角，补 Kruskal / Prim / UF 模板熟练度。
- **1203**：复刷重点是先用归一化版本（-1 分配独立 group），再对照直接建模看 trade-off。
- **1334**：不必实现可恢复缓存，回到普通 Dijkstra，但须 30 秒内复述 "discovered ≠ finalized"。
- **0815**：已收敛主流解，重点是识别速度——"反向索引 + BFS 动态邻居"应为第一反应。
- **0310**：标准写法用 `remain > 2` 判终止，比 `nextList.isEmpty()` 更直接。
- **Dijkstra 模板**（1334 / 1514 / 0778）三要点应自动化：`{node, distSnapshot}` 入队；pop 时跳过过期/已访问；pop 即 finalized。
