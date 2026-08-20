# Insight 目录索引

本目录集中存放全部横向总结，2026-07-02 由分散位置（`legacy/*/note.md`、`02-design/INSIGHT.md`、`03-advanced-array-matrix/INSIGHT.md`、旧 `insight/*/README.md`）整合而来，并统一为客观第三人称文档风格。

## 文档类型与体例

**轮次复盘**（`round-*.md`）：一轮结束后的跨题分析。骨架：摘要 → 主线 → 规则清单 → 关联。

**专题总结**（`topic-*.md`）：专题的模板与题型知识库。骨架：摘要 → 题型分类 → 模板 → 关键理解 → 题型识别 → 常见坑 → 面试表述。

**专项**（`deep-dive-*` / 其他）：单点深挖或横切缺口。

新增文档遵循同一体例；单题笔记仍留在各题目录的 README.md。

## 目录

### 轮次复盘

| 文档 | 一句话主线 |
|---|---|
| [round-01-union-find.md](round-01-union-find.md) | UF 是等价连通原语，越出语义（方向/否定/桥）必须外置或换工具 |
| [round-02-design.md](round-02-design.md) | 组合数据结构的核心是多结构同步不变量；先 put 新、后 remove 旧 |
| [round-03-advanced-array-matrix.md](round-03-advanced-array-matrix.md) | 原地编码三层级；quickselect 区分下标版/数量版停止条件 |
| [round-04-audit-r1-r12.md](round-04-audit-r1-r12.md) | 模板不衰减，衰减的是推导链与方案选择判断力 |
| [round-05-monotonic-stack.md](round-05-monotonic-stack.md) | 单调结构的本质是支配关系；相等归属先论证再写符号 |
| [round-06-math-bit-trick.md](round-06-math-bit-trick.md) | 把手工算法的中间状态显式化；补盲达成，缺口只剩实现层 |
| [round-07-design.md](round-07-design.md) | 推迟与重编码降低维护成本；延迟是否合法取决于它污染了哪条不变量 |
| [round-08-dp-cherry-pick.md](round-08-dp-cherry-pick.md) | DP 的默认语义是全枚举，功夫在裁剪判据；裁剪本身也会过度 |

### 中期复盘

| 文档 | 一句话主线 |
|---|---|
| [midterm-review-2026-07.md](midterm-review-2026-07.md) | 主线速率达标、记忆路线验证有效；债务集中在 review 积压 / UNSEEN / 默写制度三条支线 |

### 专题总结（旧轮）

| 文档 | 来源 |
|---|---|
| [topic-binary-search.md](topic-binary-search.md) | 旧 R1 |
| [topic-array-hash-two-pointers.md](topic-array-hash-two-pointers.md) | 旧 R6 |
| [topic-sliding-window.md](topic-sliding-window.md) | 旧 R5 |
| [topic-heap-priority-queue.md](topic-heap-priority-queue.md) | 旧 R3 |
| [topic-trie-and-bit.md](topic-trie-and-bit.md) | 旧 R4 |
| [topic-graph-bfs-dfs.md](topic-graph-bfs-dfs.md) | 旧 R13 |
| [topic-topo-shortest-path-mst.md](topic-topo-shortest-path-mst.md) | 旧 R14 |

### 专项

| 文档 | 主题 |
|---|---|
| [topic-direct-modeling.md](topic-direct-modeling.md) | 风格刻画：把题面约束直接翻译成状态与转移，结构贴题意而非贴范式 |
| [deep-dive-1334-resumable-dijkstra.md](deep-dive-1334-resumable-dijkstra.md) | 缓存正确性强于搜索正确性；代码留档于 [1334/](1334/) |
| [java-api-fluency.md](java-api-fluency.md) | Java 标准库速查与默写制度（横切缺口） |

## 元主线

各轮复盘收敛于同一母题——**语义与用途的精确匹配**：

```text
1334：    状态不能太弱（够用的中间状态 ≠ 可复用的权威状态）
Round 1： 工具不能越界（等价连通原语 ≠ 万能图工具）
Round 4： 状态不能太强（历史上界够用时不维护真实值）；记忆不能只剩结论（推导链先于结论蒸发）
Round 2/3：隐性约束显性化（结构同步等式；值/下标映射方向）
Round 5/8：状态不能太多（被支配者可删——单调结构与 DP 裁剪是同一命题的两种载体）
Round 7：  推迟计算的合法性 = 它是否污染了另一条不变量
```

尚缺 `topic-*` 专题总结的旧轮：`bst-tree-dp-lca`、`dp-string`、`linked-list`、`monotonic-stack-and-queue`、`stack-queue-parsing`（从未编写，非整合时遗失）。其中 dp-string 缺口最大——DP 经旧 R16-R19 三轮沉淀，却无任何专题总结。
