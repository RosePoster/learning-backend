# 专题 · 直接语义建模（Direct Semantic Modeling）

> 类型：风格刻画 ｜ 范围：`01-union-find/`、`02-design/`、`03-advanced-array-matrix/`、`04-r1-r12/`、`05-monotonic-stack/`、`06-math-bit-trick/`、`07-design/`、`legacy/` 下全部题目目录（共 **156** 题）
>
> 目标：识别「把题面约束直接翻译成状态与转移、结构贴题意而非贴经典范式」的解法，并刻画共性。不评价题解优劣。

## 判定口径

三档：

| 档 | 含义 |
|---|---|
| **A** | 实质不同：状态定义、扫描方向、维护量或正确性论证依据与主流解不同（非实现细节） |
| **B** | 同构但表述不同：算法本质相同，变量组织或代码风格有别 |
| **C** | 标准解：套用通行模板 |

**A 信号**（满足任意两条即可；拿不准归 **B**）：

1. README 明确对照主流 / 「另一种角度」/ 「我的思路是」类表述
2. 「主流解对照」所写范式与 `Solution*.java` 实际结构不一致
3. 代码引入主流解中不存在的中间量、计数器、状态变量
4. 正确性依赖作者自推不变量，而非该范式标准论证
5. 主流需要的分类被统一量消掉，或反之出现主流不需要的分类讨论

**多解文件**：目录内存在多个 `Solution*.java` 时，以最能体现「直接语义建模」的版本定级（例如 0992→Sol1、1249→Sol2、1944→`Solution-pre`）。仅出现在 README 叙事、未落入任何 `Solution*.java` 的中间思路（如 0581 v1、1334 缓存复用）不升为 A。

---

## 第一步：逐题判定

### 01-union-find（13）

| 题 | 档 | 一句话 |
|---|---|---|
| 0399 Evaluate Division | C | 带权图 DFS，主流范式之一 |
| 0547 Number of Provinces | C | UF / 连通分量模板 |
| 0685 Redundant Connection II | C | 入度扫描 + 候选边 UF 测环，与主流同构 |
| 0721 Accounts Merge | C | email 离散化 + UF + 按 root 聚合 |
| 0827 Making A Large Island | C | 陆地 UF 面积 + 枚举翻转 0 |
| 0839 Similar String Groups | C | 两两相似 + UF |
| 0959 Regions Cut by Slashes | B | 同为 UF 分格；2-part 半区是 4-三角 / 3× 放大的压缩编码 |
| 0990 Satisfiability of Equality Equations | C | 先 `==` union 再查 `!=` |
| 1202 Smallest String With Swaps | C | 交换边 UF + 组内排序填回 |
| 1319 Network Connected | C | 边数下界 + components−1；显式冗余线缆是语义展开，算法同构 |
| **1489 Critical / Pseudo-Critical MST Edges** | **A** | 同权批 + 超级节点 + Tarjan 桥 vs 每边双 Kruskal |
| 1971 Find if Path Exists | C | 裸连通性 |
| 2685 Complete Components | C | UF 维护 size/edgeCount + 完全图公式 |

### 02-design（7）

| 题 | 档 | 一句话 |
|---|---|---|
| 0146 LRU Cache | C | HashMap + 双向链表 |
| 0173 BST Iterator | C | 受控中序栈 |
| 0380 Insert Delete GetRandom O(1) | C | list + map 尾覆盖删 |
| 0460 LFU Cache | C | freq 桶 + minFreq |
| 0622 Design Circular Queue | B | 预分配环形链表 vs 更常见环形数组，空满判定同构 |
| 0705 Design HashSet | C | 分桶拉链 |
| 0706 Design HashMap | C | 分桶拉链 |

### 03-advanced-array-matrix（13）

| 题 | 档 | 一句话 |
|---|---|---|
| 0041 First Missing Positive | B | 原地哈希同构；非法位置置 0 是风格 |
| 0048 Rotate Image | B | 四点轮换 vs 翻转+转置，同族 |
| 0054 Spiral Matrix | B | layer 分层 vs 四边界收缩，等价 |
| 0057 Insert Interval | C | 三段扫描 |
| 0073 Set Matrix Zeroes | C | 首行首列标记 |
| 0080 Remove Duplicates II | B | 双指针同框架；boolean 态 vs 写指针−2 |
| 0086 Partition List | C | 双链表 stable partition |
| 0179 Largest Number | C | 落盘为 pairwise comparator（补位思路已否决） |
| 0220 Contains Duplicate III | C | 滑窗 + TreeMap |
| 0289 Game of Life | C | 状态编码原地更新 |
| 0324 Wiggle Sort II | C | quickselect 中位 + 倒序交叉 |
| 0912 Sort an Array | C | 排序模板 |
| 0973 K Closest Points | C | 堆 / quickselect |

### 04-r1-r12（23）

| 题 | 档 | 一句话 |
|---|---|---|
| 0025 Reverse Nodes in k-Group | C | k 组反转 |
| 0033 Search in Rotated Sorted Array | C | 有序侧二分 |
| 0046 Permutations | C | 回溯 |
| 0047 Permutations II | C | 同层去重 |
| 0056 Merge Intervals | C | 排序合并 |
| 0079 Word Search | C | 网格 DFS |
| 0092 Reverse Linked List II | C | 区间反转 |
| 0103 Zigzag Level Order | B | 双 deque 反转入队 vs 单队列 + addFirst/addLast |
| 0124 Binary Tree Maximum Path Sum | C | 树 DP 路径贡献 |
| 0138 Copy List with Random Pointer | C | 插入复制节点 O(1) 三步法 |
| 0142 Linked List Cycle II | C | 快慢指针环入口 |
| 0153 Find Minimum in Rotated Sorted Array | C | 删不含最小值的有序段 |
| 0162 Find Peak Element | B | 坡度二分；三点 vs mid/mid+1 |
| 0199 Right Side View | C | 层序 / 根右左 |
| 0230 Kth Smallest in BST | C | 中序第 k |
| 0235 LCA of BST | C | 值域走向 |
| 0236 LCA of Binary Tree | B | boolean + 全局 ans vs 节点返回值兼载 |
| 0287 Find the Duplicate Number | C | 快慢指针环入口 |
| 0297 Serialize / Deserialize BT | C | 层序 + 空节点 |
| 0347 Top K Frequent | C | 频率桶 |
| 0424 Longest Repeating Character Replacement | B | 滑窗同骨架；真实 maxFreq+buckets vs 历史 maxFreq |
| 0437 Path Sum III | C | 树前缀和 |
| 0560 Subarray Sum Equals K | C | 前缀和 HashMap |

### 05-monotonic-stack（10）

| 题 | 档 | 一句话 |
|---|---|---|
| 0321 Create Maximum Number | B | 枚举分配 + 合并框架与主流同；子序列用 nextGreater 跳而非 drop |
| 0456 132 Pattern | B | 作 2 枚举 + previous greater + 左侧最低；与 RTL 候选 2 同属 mono 栈 132 |
| 0735 Asteroid Collision | B | 碰撞语义同；左行 list + 右行栈 vs 单栈 |
| **0862 Shortest Subarray with Sum ≥ K** | **A** | 支配关系 + TreeMap vs 单调队列 |
| 0901 Online Stock Span | C | 单调栈跨度 |
| 1673 Most Competitive Subsequence | C | 可删预算 + drop |
| 1856 Maximum Subarray Min-Product | C | min 贡献边界 + 前缀和 |
| **1944 Number of Visible People** | **A** | `Solution-pre`：nextHigher 链计数；主 `Solution` 已收敛为弹栈主流 |
| 2104 Sum of Subarray Ranges | C | max 贡献 − min 贡献 |
| **2398 Maximum Robots Within Budget** | **A** | 滑窗 + nextGreater/最大下标 vs mono deque 窗 max |

### 06-math-bit-trick（9）

| 题 | 档 | 一句话 |
|---|---|---|
| 0008 String to Integer (atoi) | C | 扫描解析 |
| 0029 Divide Two Integers | B | 倍增减除同质；自适应移 vs bit31 固定下行 |
| 0043 Multiply Strings | B | 竖式同质；char[] 即时进位 vs int[] 先累后进 |
| 0050 Pow(x, n) | C | 快速幂 |
| 0165 Compare Version Numbers | C | 分段比较 |
| 0166 Fraction to Recurring Decimal | B | 长除 + 余数 map 同质；手写减法代替 `/` `%` |
| 0172 Factorial Trailing Zeroes | C | 数因子 5 |
| 0273 Integer to English Words | C | 三位一节 |
| 0415 Add Strings | C | 低位加法模拟 |

### 07-design（6）

| 题 | 档 | 一句话 |
|---|---|---|
| 0284 Peeking Iterator | C | 预取缓存 |
| 0341 Flatten Nested List Iterator | C | (list, idx) 栈懒展开 |
| 0381 Insert Delete GetRandom — Duplicates | C | list + val→index-set 尾交换 |
| 0641 Design Circular Deque | C | 环形数组/链表 |
| 0895 Maximum Frequency Stack | C | freqMap + freq→stack |
| 1381 Stack With Increment | B | 差分单数组 vs stack+inc 懒标记，同目标不同编码 |

### legacy/array-hash-two-pointers（9）

| 题 | 档 | 一句话 |
|---|---|---|
| 0016 3Sum Closest | C | 排序 + 双指针 |
| 0018 4Sum | C | 排序 + 双层枚举 + 双指针 |
| 0036 Valid Sudoku | C | 行/列/宫标记 |
| 0167 Two Sum II | C | 有序 dual pointer |
| 0274 H-Index | C | 答案域 [0,n] 计数累计 |
| 0334 Increasing Triplet | C | 最小 one/two 候选 |
| 0442 Find All Duplicates | C | 下标符号位标记 |
| 0443 String Compression | B | 按段压缩；循环组织不同 |
| 0581 Shortest Unsorted Continuous Subarray | C | 落盘为双向 max/min 定界主流；插入模拟 v1 未入 Solution |

### legacy/binary-search · bst · dp · linked-list · monotonic（6）

| 题 | 档 | 一句话 |
|---|---|---|
| 0540 Single Element in Sorted Array | B | 完整 pair 区间奇偶 ≡ mid 下标奇偶 |
| 0124 Binary Tree Maximum Path Sum | C | 树 DP 路径 |
| 0072 Edit Distance | C | 编辑距离 DP |
| 0139 Word Break | C | 可达性 DP |
| 0025 Reverse Nodes in k-Group | C | k 组反转 |
| 0042 Trapping Rain Water | C | 双指针 leftMax/rightMax |

### legacy/graph-bfs-dfs（15）

| 题 | 档 | 一句话 |
|---|---|---|
| 0127 Word Ladder | C | pattern 建图 + BFS |
| 0130 Surrounded Regions | C | 边界反向 flood |
| 0417 Pacific Atlantic | C | 双海 multi-source BFS |
| 0529 Minesweeper | C | 规则模拟 + 展开 |
| 0542 01 Matrix | C | multi-source BFS |
| 0695 Max Area of Island | C | 连通分量面积 |
| 0733 Flood Fill | C | flood fill |
| 0752 Open the Lock | C | 隐式图 BFS |
| 0909 Snakes and Ladders | C | redirect + BFS |
| 0934 Shortest Bridge | C | 标岛 + 多源扩桥 |
| 0994 Rotting Oranges | C | multi-source 层序 |
| 1091 Shortest Path Binary Matrix | C | 网格 BFS |
| 1162 As Far from Land | C | multi-source 最大距离 |
| 1293 Path with Obstacles Elimination | C | 状态带资源 BFS |
| 1926 Nearest Exit | C | 网格 BFS |

### legacy/heap-priority-queue（5）

| 题 | 档 | 一句话 |
|---|---|---|
| 0355 Design Twitter | C | 关注图 + k 路堆归并 |
| 0480 Sliding Window Median | C | 双堆 + lazy delete |
| 0621 Task Scheduler | C | free/freeze 调度模拟 |
| 0767 Reorganize String | C | 最大堆 + 冷却 |
| 2402 Meeting Rooms III | C | 双堆资源调度 |

### legacy/sliding-window（8）

| 题 | 档 | 一句话 |
|---|---|---|
| 0424 Longest Repeating Character Replacement | C | 历史 maxCount 不回退 |
| 0567 Permutation in String | C | 定长窗口频次 |
| 0643 Maximum Average Subarray I | C | 定长求和 |
| 0713 Subarray Product Less Than K | C | 乘积窗 + 右端贡献 |
| **0992 Subarrays with K Different Integers** | **A** | Sol1：exactly-k 最短窗 + `redundantPre`；Sol2 为 atMost 差分 |
| 1004 Max Consecutive Ones III | C | 窗内 0 ≤ k |
| 1208 Equal Substring Within Budget | B | 同窗；if 同步 left vs while 收缩 |
| 1456 Maximum Number of Vowels | C | 定长计元音 |

### legacy/stack-queue-parsing（6）

| 题 | 档 | 一句话 |
|---|---|---|
| 0224 Basic Calculator | C | 递归下降 parser |
| 0225 Stack using Queues | C | 队列模拟栈 |
| 0227 Basic Calculator II | B | 分层 parser vs 栈/lastNum 同族 |
| 0316 Remove Duplicate Letters | C | 贪心栈 + lastIndex |
| 0394 Decode String | B | 全局 index 递归 vs 栈；推进责任是风格 |
| **1249 Min Remove to Make Valid Parentheses** | **A** | Sol2：位置/数量失配 → 删除额度；Sol1 为两遍扫描族 |

### legacy/topo-shortest-path-mst（12）

| 题 | 档 | 一句话 |
|---|---|---|
| 0210 Course Schedule II | C | Kahn |
| 0310 Minimum Height Trees | B | 同步剥叶子；终止条件变体 |
| 0778 Swim in Rising Water | C | 优先队列 Dijkstra/Prim 式 |
| 0797 All Paths Source to Target | B | 回溯；path 职责在 dfs 内/外 |
| 0802 Find Eventual Safe States | B | 三色 DFS 状态压缩 |
| 0815 Bus Routes | C | stop→routes + 动态邻居 BFS |
| **1203 Sort Items by Groups** | **A** | 在线双层调度保留 -1 语义 vs -1→solo group 二重拓扑 |
| 1334 City With Smallest Neighbors | C | 落盘为多源 Dijkstra；缓存复用未入 Solution |
| **1368 Min Cost Valid Path in Grid** | **A** | 0-cost 箭头闭包分层 vs deque 0-1 BFS |
| 1514 Path with Maximum Probability | C | 最大概率 Dijkstra |
| 1584 Min Cost Connect All Points | C | Kruskal/Prim + UF |
| 2115 Find All Possible Recipes | C | 反转依赖 + Kahn |

### legacy/trie-and-bit（14）

| 题 | 档 | 一句话 |
|---|---|---|
| 0136 Single Number | C | 全异或 |
| 0137 Single Number II | C | ones/twos 模三 |
| 0191 Number of 1 Bits | C | `n&=n-1` |
| 0208 Implement Trie | C | Trie 模板 |
| 0211 Design Add and Search Words | C | Trie + `.` 分支 |
| 0212 Word Search II | C | 网格 DFS + Trie |
| 0231 Power of Two | C | `n&(n-1)==0` |
| 0260 Single Number III | C | 总异或 + 分组 |
| 0318 Maximum Product of Word Lengths | C | bitmask 聚类 |
| 0338 Counting Bits | C | `ans[i]=ans[i>>1]+(i&1)` |
| 0421 Maximum XOR of Two Numbers | C | 二进制 Trie |
| 0648 Replace Words | C | Trie 词根 |
| 0677 Map Sum Pairs | C | Trie 前缀和 |
| 1707 Maximum XOR With Element | B | 离线 Trie 同构；返回 matchedNum 再异或 |

---

## 第二步：A 类条目

共 **8** 题。

### 1489 · Find Critical and Pseudo-Critical Edges in MST ｜ `01-union-find/`

- **主流解形式**：先求基准 MST 权；对每条边分别「禁用 / 强制加入」再跑 Kruskal，用权是否变大或能否连通判定 critical / pseudo-critical。
- **作者解形式**：边按权分批；批前 UF 分量压成超级节点，批内跨分量边建临时多重图，Tarjan 求桥（桥=critical，非桥=pseudo），整批分类完再 union 进全局 UF。
- **核心差异点**：正确性依据从「逐边对 MST 权做反事实实验」变为「同权竞争下的桥/环分类」；维护量从全局 MST 权变为批前连通分量 + 临时图 low/dfn。
- **承重不变量**：「更小权边已在全局 UF 中定型；两端已连通的同权边永不入任何 MST；同权边才可互相替代；临时图上的桥 = 该权下不可替代连接；重边须用 parentEdgeId 跳过。」（README 关键点归纳）
- **复杂度对比**：作者 \(O(m\log m + m\alpha(n))\) vs 主流约 \(O(m^2\alpha(n))\)——作者更优，无劣化。
- **是否更难自证**：是。需同时证分批语义、超级节点缩并、多重图 Tarjan 桥；面试更稳的是双 Kruskal。

### 0862 · Shortest Subarray with Sum at Least K ｜ `05-monotonic-stack/`

- **主流解形式**：前缀和 + 单调递增 deque，摊还 \(O(n)\) 维护「可成为左端」的候选前缀。
- **作者解形式**：前缀状态入 TreeMap；按支配关系删除被支配态，使 pre 升则 idx 升；`floorKey(currSum−k)` 取最大合法左端。\(O(n\log n)\)。
- **核心差异点**：同一支配关系的物化介质不同（deque 摊还 vs 有序表显式剪枝 + 有序查询）；查询从队头 pop 变为 `floorKey`。
- **承重不变量**：「若 `preSum1 ≤ preSum2` 且 `idx1 ≥ idx2`，则状态 2 永不更优，可删；维护后 TreeMap 中 preSum 递增时 idx 也递增，故 `floorKey(currSum−k)` 对应满足条件的最大下标。」（README 原文要点）
- **复杂度对比**：作者 \(O(n\log n)\) vs 主流 \(O(n)\)——**有劣化**。
- **是否更难自证**：是。需先发明支配剪枝再接到 TreeMap；主流 mono queue 论证更短。

### 2398 · Maximum Number of Robots Within Budget ｜ `05-monotonic-stack/`

- **主流解形式**：连续区间 → 滑窗；窗内 `max(charge)` 用单调递减 deque，配合 runSum 判断预算。
- **作者解形式**：同滑窗外壳；预处理 nextGreater；维护最新出现的最大 charge 下标，仅当该下标离开窗口时沿 nextGreater 链重寻窗 max。
- **核心差异点**：窗 max 的状态与扫描——deque 在线支配 vs nextGreater 链 + 最大下标单调推进；摊还论证依赖「搜索起点全局右移」。
- **承重不变量**：「维护最新出现的最大值下标；仅当当前最大值真正离开窗口时沿 nextGreater 重寻；最大值下标始终单调向右，避免相等元素重复扫描。」（README 关键点 / 卡点）
- **复杂度对比**：均为 \(O(n)\) 时间、\(O(n)\) 空间；作者多一张 nextGreater，证明更重，阶不劣。
- **是否更难自证**：是。需额外证链上摊还与相等元素陷阱；面试默认 mono deque。

### 1944 · Number of Visible People in a Queue ｜ `05-monotonic-stack/`（`Solution-pre.java`）

- **主流解形式**：从右往左单调栈，弹栈计数可见更矮者，弹完后栈顶再计一次阻挡者（主 `Solution.java` 即此）。
- **作者解形式**（pre）：预处理 nextHigher；对每个 `curr` 沿 nextHigher 递增链跳跃计数，直到第一个更高阻挡者。
- **核心差异点**：状态定义是「可见 = 沿 nextHigher 的递增链」而非「弹栈事件」；复杂度论证从「每元素进出栈一次」换成「可见关系总数 = O(n)」。
- **承重不变量**：「`curr` 能看到的右侧元素是一条沿 nextHigher 形成的递增链，直到第一个大于 `curr` 的人；沿链 `ans[i]++` 的总次数等于所有可见关系数量，而可见关系总数是线性的。」（README 关键点）
- **复杂度对比**：均为 \(O(n)\)；无劣化。
- **是否更难自证**：是。链版本贴题意，但摊还需多解释一层「可见关系总数线性」；面试优先弹栈版。

### 1203 · Sort Items by Groups Respecting Dependencies ｜ `legacy/topo-shortest-path-mst/`

- **主流解形式**：每个 `group[i]==-1` 的 item 分配独立 solo group，统一做 group 层拓扑 + item 层拓扑后拼接。
- **作者解形式**：保留 `-1` 原始语义的在线双层调度：`taskDegree` / `groupExternalDegree` / `readyGroup` / `readyNoGroupTask` / `activeQueue`。
- **核心差异点**：主流先做抽象归一化消特殊分支；作者按题面在线推进——group 外部依赖门禁 + 激活后同组连续输出 + 无组自由流，状态集合膨胀。
- **承重不变量**：「`taskDegree[i]` 表示 item 未完成前置数；`groupExternalDegree[g]` 表示 group 尚未完成的**外部依赖边**数量（不是组内 item 数）；group 仅在 external degree==0 时激活，激活后只处理该 group 内部 ready item，保证同组连续。」（README）
- **复杂度对比**：均为 \(O(n+m+E)\) 量级；作者常数与分支更多，阶不劣。
- **是否更难自证**：是。多队列联立推进，正确性依赖多个不变量；README 明确证明/实现成本高于二重拓扑。

### 1249 · Minimum Remove to Make Valid Parentheses ｜ `legacy/stack-queue-parsing/`（Solution2）

- **主流解形式**：(1) 栈记未匹配下标后删除；(2) 两遍扫描：左→右删非法 `)`，右→左删多余 `(`（Sol1 族）。
- **作者解形式**：先统计 `leftCount/rightCount/misMatch`，拆成位置失配 + 数量失配，算出 `deleteRight` 与可保留左括号额度，单遍按配额构造答案。
- **核心差异点**：主流在扫描中做匹配/修复；作者先算删除配额再贪心消费（右优先靠左删，左用保留额度等价靠右删）。
- **承重不变量**：「任意前缀中右括号不能多于左括号；最终左括号不能多余。`misMatch` 为无法被任何左匹配的右；删除这些后按剩余左右数量决定额外删除哪一侧；构造时配额控制保留。」（README 关键点）
- **复杂度对比**：时间均为 \(O(n)\)；Sol2 额外空间 \(O(1)\)（不计输出），空间更优。
- **是否更难自证**：是。位置失配与数量失配易混；README 写明面试更易讲 Sol1 两遍扫描。

### 0992 · Subarrays with K Different Integers ｜ `legacy/sliding-window/`（Solution1）

- **主流解形式**：`atMost(k) − atMost(k−1)`（Solution2 即此）。
- **作者解形式**：单窗口维护 exactly-k 的最短合法段 `[left,right]`，另用 `redundantPre` 标记左侧冗余前缀；`ans += left − redundantPre + 1`。
- **核心差异点**：主流两次「至多 k 种」差分；作者直接数「以 right 结尾、exactly k」的左端点区间，依赖最短窗与冗余前缀双指针。
- **承重不变量**：「`[left,right]` 为恰好 k 种不同整数的最短窗；`redundantPre..left` 间任意起点与 right 组成合法子数组；`diffNums>k` 时弹出一种并重置 `redundantPre`，再压缩 `counts[left]>1` 的冗余。」（README 关键点）
- **复杂度对比**：均为 \(O(n)\)；Sol1 一次遍历，Sol2 两次 atMost。
- **是否更难自证**：是。`redundantPre` 与最短窗交互比 atMost 差分更难一次讲清。

### 1368 · Minimum Cost to Make at Least One Valid Path in a Grid ｜ `legacy/topo-shortest-path-mst/`

- **主流解形式**：0-1 BFS——箭头方向边权 0 入队头，改向边权 1 入队尾，`dist[][]` 最短修改次数。
- **作者解形式**：未见 0-1 BFS 时从题结构推出：`visit` 顺箭头吃掉当前 cost 的 0-cost 闭包；外层 BFS 一层 = 全体闭包向外改向一次（cost+1）。
- **核心差异点**：主流单 deque 混 0/1 边；作者显式分层 + 箭头滑行闭包（每格仅一条 0-out，可 while 滑行）。维护量：`visited + 层队列` vs `dist + deque`。
- **承重不变量**：「沿箭头 cost=0，可在当前层一次展开；闭包内任格四向一步 = 改向 cost+1；外层层数 = 修改次数；终点首次 visited 即最小代价；闭包每格都要入队作下一层出发点。」（README 关键点）
- **复杂度对比**：均为 \(O(nm)\)；无劣化。思想与 0-1 BFS 等价，结构不同。
- **是否更难自证**：略是。贴题结构后自证路径清晰，但相对「默写 0-1 BFS 模板」多一层闭包—分层对应关系。

---

## 第三步：全局汇总

### 1. 三类数量与 A 占比

| 档 | 题数 | 占比 |
|---|---:|---:|
| **A** | 8 | **5.1%** |
| **B** | 29 | 18.6% |
| **C** | 119 | 76.3% |
| 合计 | 156 | 100% |

A 题号一览：`1489`、`0862`、`2398`、`1944`、`1203`、`1249`、`0992`、`1368`。

### 2. A 类在专题上的分布

| 专题目录 | A 题数 | 题号 |
|---|---:|---|
| `05-monotonic-stack/` | 3 | 0862, 1944, 2398 |
| `legacy/topo-shortest-path-mst/` | 2 | 1203, 1368 |
| `01-union-find/` | 1 | 1489 |
| `legacy/sliding-window/` | 1 | 0992 |
| `legacy/stack-queue-parsing/` | 1 | 1249 |

**集中**：单调栈变体（3）、拓扑/最短路建模（2）。  
**零 A 的专题**：`02-design/`、`03-advanced-array-matrix/`、`04-r1-r12/`、`06-math-bit-trick/`、`07-design/`，以及 legacy 中的 `graph-bfs-dfs/`、`heap-priority-queue/`、`array-hash-two-pointers/`、`binary-search/`、`bst-tree-dp-lca/`、`dp-string/`、`linked-list/`、`monotonic-stack-and-queue/`、`trie-and-bit/`。

模板密度高、范式边界清晰的轮次（BFS 网格、经典 design、bit/Trie、r1–r12 审计题）几乎全部落在 B/C；A 出现在「约束多、主流依赖一次归一化或专用结构」的题上。

### 3. 「承重不变量」来源统计

| 来源 | 题数 | 比例（相对 8 道 A） | 题号 |
|---|---:|---:|---|
| README 显式写出 | 8 | **100%** | 全部 A 题 |
| 需从代码反推 | 0 | 0% | — |
| 反推不出 | 0 | 0% | — |

A 类题目的 README 普遍已把关键命题写进「关键点 / 我的思考」，与「正确性依赖自推不变量」这一信号一致；本轮无需反推。

### 4. 反复出现的手法（≥2 次）

| 手法 | 出现题 | 说明 |
|---|---|---|
| **nextGreater / nextHigher 链作为主结构** | 1944, 2398（0321 为同手法但整题定 B） | 把「最近更大」预处理成跳转表，再沿链做计数或窗 max 重寻，而非在线弹栈/deque |
| **题面约束编码成显式配额 / 计数器** | 1249 (`misMatch`/`deleteRight`), 0992 (`redundantPre`/`diffNums`), 1203 (`groupExternalDegree`/`taskDegree`) | 用标量或少量计数器消掉栈/差分/归一化带来的隐式状态 |
| **保留原始题意、拒绝提前归一化** | 1203 (保留 `-1`), 1249 (位置失配+数量失配), 0992 (直接 exactly-k) | 不先做「solo group / atMost 差分 / 两遍扫描」等抽象，直接按题面角色推进 |
| **从支配关系自推可删状态** | 0862 (preSum–idx 支配), 2398 (窗 max 下标单调右移) | 先写「谁永不更优可删」，再选 TreeMap / 链 / deque 等物化介质 |
| **分层 / 批处理把耦合约束拆开** | 1489 (同权批), 1368 (cost 层 + 0-cost 闭包), 1203 (item 层 + group 门禁) | 固定一维（权值层 / 修改次数层 / 调度层）后再处理批内结构 |

### 5. 复杂度劣于主流解的 A 题

| 题 | 作者 | 主流 | 劣化 |
|---|---|---|---|
| **0862** | \(O(n\log n)\) TreeMap | \(O(n)\) 单调队列 | **是（时间多一个 log）** |

其余 7 题与主流同阶或更优（1489 严格更优；1249 Sol2 空间更优；其余时间同阶）。

---

## 风格画像（事实归纳，非评价）

在 156 题中，**直接语义建模**是少数派（约 1/20），但分布不随机：

1. **启动路径**：先按题面角色写状态（配额、门禁、闭包、可见链），再在实现中物化；主流往往先选范式（单调队列、二重拓扑、atMost 差分、0-1 BFS）再填细节。
2. **中间量更「贴词」**：`misMatch`、`redundantPre`、`groupExternalDegree`、`nextHigher`、超级节点临时图——命名与题面名词对齐，而不是范式术语（deque front、solo group、atMost）。
3. **正确性载体**：README 对 A 题 100% 写出承重不变量；自证成本普遍高于同题主流写法（8 题中 7 题明确「更难自证」）。
4. **与 B 的边界**：同一范式内的编码变体（2-part UF、真实 maxFreq、boolean LCA、pair 区间二分）一律归 B；只有范式级分叉才进 A。
5. **叙事有、落盘无**：0581 插入模拟、1334 缓存 Dijkstra 等在笔记中展现同风格，但最终 `Solution.java` 已收敛主流，按口径不计入 A。

---

## 关联

- [round-05-monotonic-stack.md](round-05-monotonic-stack.md)：支配关系、nextGreater 个人变体、0862/1944/2398 的证明成本核算。
- [round-01-union-find.md](round-01-union-find.md)：1489 作为 UF 语义边界外「桥 / 环」需换工具的代表。
- [deep-dive-1334-resumable-dijkstra.md](deep-dive-1334-resumable-dijkstra.md)：缓存复用式直接建模的未落盘实验（本统计不标 A）。
- [topic-sliding-window.md](topic-sliding-window.md)：0992 的 atMost 差分与 exactly-k + 冗余前缀对照。
