# 专题 · Trie / 位运算

> 类型：专题总结 ｜ 来源：旧 Round 4

## 摘要

两个子主题在二进制 Trie（异或优化）上交汇。Trie 的核心认知：边表示字符、节点表示状态；位运算的核心积木：`n & (n-1)` 消最低位 1、`n & -n` 提最低位 1。

## Part A · Trie

### 题型分类

| 类型 | 适用 | 核心 | 代表题 |
|---|---|---|---|
| 字符串 Trie | 前缀查找、单词匹配、前缀替换 | 标准 insert / findNode | 0208 / 0211 / 0648 / 0677 |
| Trie + DFS | 二维网格搜索多个单词 | Trie 压缩词表，DFS 同步推进，无后继剪枝 | 0212 |
| 二进制 Trie | 最大异或值 | 按位从高到低建树，查询时每位贪心走相反分支 | 0421 / 1707 |

### 节点结构与基本操作

```java
class TrieNode {
    TrieNode[] next = new TrieNode[26]; // 或 Map<Character, TrieNode>
    boolean isEnd;
    String word; // 可选：0212 直接收集答案
    int val;     // 可选：0677 前缀和
}

void insert(String word) {
    TrieNode curr = root;
    for (char c : word.toCharArray()) {
        if (curr.next[c - 'a'] == null) curr.next[c - 'a'] = new TrieNode();
        curr = curr.next[c - 'a'];
    }
    curr.isEnd = true;
}

TrieNode findNode(String prefix) {
    TrieNode curr = root;
    for (char c : prefix.toCharArray()) {
        if (curr.next[c - 'a'] == null) return null;
        curr = curr.next[c - 'a'];
    }
    return curr;
}
// search = findNode + isEnd；startsWith = findNode != null
```

`next[26]` 适合字符集固定的场景；`Map` 适合需要 `size()/remove()` 剪枝的场景（0212）。

### 通配符搜索（纯递归）

```java
boolean isMatch(String word, int index, TrieNode node) {
    if (index == word.length()) return node.isEnd;
    char c = word.charAt(index);
    if (c == '.') {
        for (TrieNode child : node.next)
            if (child != null && isMatch(word, index + 1, child)) return true;
        return false;
    }
    return node.next[c - 'a'] != null && isMatch(word, index + 1, node.next[c - 'a']);
}
```

### Trie + DFS（0212）

```java
void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
    char ch = board[r][c];
    TrieNode next = node.next[ch - 'a'];
    if (next == null) return;                  // 无后继剪枝
    if (next.word != null) { result.add(next.word); next.word = null; } // 去重
    board[r][c] = '#';                         // 原地标记
    for (int[] dir : DIRS) {
        int nr = r + dir[0], nc = c + dir[1];
        if (inBounds(nr, nc) && board[nr][nc] != '#') dfs(board, nr, nc, next, result);
    }
    board[r][c] = ch;                          // 回退
    if (isEmpty(next) && next.word == null) node.next[ch - 'a'] = null; // 回退时删空节点
}
```

### 二进制 Trie（最大异或）

```java
class BitTrie {
    int[][] next; int cnt = 0; int maxBitPos;

    void insert(int num) {
        int curr = 0;
        for (int i = maxBitPos; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (next[curr][bit] == 0) next[curr][bit] = ++cnt;
            curr = next[curr][bit];
        }
    }
    int queryMaxXor(int num) {
        int curr = 0, xor = 0;
        for (int i = maxBitPos; i >= 0; i--) {
            int bit = (num >> i) & 1, want = bit ^ 1;
            if (next[curr][want] != 0) { xor |= (1 << i); curr = next[curr][want]; }
            else curr = next[curr][bit];
        }
        return xor;
    }
}
```

离线查询 + 上界约束（1707）：nums 排序、queries 按 m 升序，随 m 增大逐步插入满足条件的 nums，每个数只入 Trie 一次。

### 关键理解

1. **边存字符、节点存状态**：处理字符先沿边移动到下一节点，再在节点上判断；反向操作导致控制流回退（0648）。
2. 通配符匹配统一用纯递归，混合半迭代半递归可读性差（0211）。
3. 节点 val 可设计为路径累加值（0677 前缀和，插入时记录旧值算 delta）。
4. 找到答案后的 Trie 清理：`word = null` 去重 + 回退时删除无后继非终点的子节点（0212）。
5. 二进制 Trie 必须从最高有效位开始：异或比大小高位优先。
6. 离线查询把约束转化为单调扩张（1707）。

## Part B · 位运算

### 题型分类与模板

异或消除（出现两次，0136）：全体异或，成对抵消。

模 3 状态机（出现三次，0137）：

```java
int ones = 0, twos = 0;
for (int num : nums) {
    ones = (ones ^ num) & ~twos;
    twos = (twos ^ num) & ~ones;
}
return ones;
// 某位 num=1 时状态流转：(ones,twos) = (0,0)→(1,0)→(0,1)→(0,0)
// & ~twos：twos 为 1 的位禁止 ones 更新
```

按差异位分组（两个 single number，0260）：

```java
int xor = 0;
for (int num : nums) xor ^= num;
int divider = xor & -xor; // 最低差异位
int a = 0;
for (int num : nums) if ((num & divider) != 0) a ^= num;
return new int[]{a, xor ^ a};
```

位掩码集合判交（0318）：

```java
Map<Integer, Integer> maskToMaxLen = new HashMap<>();
for (String w : words) {
    int mask = 0;
    for (char c : w.toCharArray()) mask |= 1 << (c - 'a');
    maskToMaxLen.merge(mask, w.length(), Math::max);
}
// mask1 & mask2 == 0 即无公共字符
```

低位操作与位递推：

```java
n & (n - 1)     // 消最低位 1（0191 计数；0231 结果为 0 即 2 的幂）
n & -n          // 提最低位 1（0260 分组依据）
(num >> i) & 1  // 取第 i 位；1 << i 置第 i 位
ans[i] = ans[i >> 1] + (i & 1); // 0338 位递推
```

### 关键理解

1. 异或 = 不进位二进制加法，成对抵消是 Single Number 系列的基础。
2. 出现 k 次的通用思路是模 k 状态机：k=2 即异或，k=3 需 ones/twos 互相屏蔽。
3. `n & (n-1)` 与 `n & -n` 是位操作基础积木。
4. 元素种类有限时集合可压缩为 int：并 `|`、交 `&`、判交 `& == 0`；相同 mask 只留最优值。
5. 位递推利用 `i >> 1`（去最低位）的子问题关系。

## 题型识别

| 特征 | 优先考虑 |
|---|---|
| 前缀匹配 / 查找 | Trie |
| 通配符匹配 | Trie + 递归 |
| 网格搜索多单词 | Trie + DFS |
| 最大异或值 | 二进制 Trie 高位贪心 |
| 带上界的异或查询 | 离线排序 + 二进制 Trie |
| 出现 k 次 / 1 次 | 异或 / 模 k 状态机 |
| 集合是否相交 | 位掩码 `&` |
| 数 1 / 判 2 的幂 | `n & (n-1)` |

## 常见坑

1. 在当前节点判断未知边 → 控制流回退；先移动再判断（0648）。
2. 找到单词后停止搜索 → 更长单词可能以它为前缀（0212）。
3. 未标记已访问格子 → 同一路径重复用格（0212）。
4. Trie 与 TrieNode 职责混淆 → `maxBitPos` 类全局信息放 Trie 类（0421）。
5. 模 3 状态机 ones/twos 更新顺序不可颠倒（0137）。
6. `n & -n` 要求 `n != 0`（0260）。
7. 位掩码不能按超集关系合并：子集可能有超集无法配对的更优对象（0318）。

## 面试表述

Trie：相同前缀共享路径，插入/查找沿字符逐级下行，复杂度与串长成正比，`isEnd` 区分前缀与完整单词。二进制 Trie：按位从高到低插入，查询每位贪心走相反方向——高位的 1 大于低位所有位之和。异或消除：成对抵消；两个 single number 时先全体异或，再按任一差异位分组、组内异或。模 3 状态机：ones/twos 模拟每位出现次数 mod 3，互相屏蔽实现三态循环。
