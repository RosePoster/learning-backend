# Trie / 位运算核心总结

本专题包含两个子主题：**Trie（前缀树）** 和 **位运算**。二者在二进制 Trie（异或优化）上交汇。

---

## Part A：Trie

### 题型分类

#### 一、字符串 Trie（基础匹配）
- 适用：前缀查找、单词匹配、前缀替换
- 代表题：`208`、`211`、`648`、`677`

#### 二、字符串 Trie + DFS/回溯
- 适用：在二维网格上搜索多个单词
- 核心：用 Trie 压缩词表，DFS 时在 Trie 上同步推进，无后继则剪枝
- 代表题：`212`

#### 三、二进制 Trie（异或优化）
- 适用：最大异或值查询
- 核心：将数字按二进制位从高到低插入 Trie，查询时每位贪心走相反分支
- 代表题：`421`、`1707`

### 代码模板

#### Trie 节点结构

```java
class TrieNode {
    TrieNode[] next = new TrieNode[26]; // 或 Map<Character, TrieNode>
    boolean isEnd;
    String word; // 可选：212 中用于直接收集答案
    int val;     // 可选：677 中用于前缀和
}
```

`next[26]` 适合字符集固定（a-z）的场景，常数时间更优。  
`Map<Character, TrieNode>` 适合需要 `size()` / `remove()` 做剪枝的场景（如 `212`）。

#### 插入 / 查找

```java
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
// search = findNode + check isEnd
// startsWith = findNode != null
```

#### 通配符搜索（递归）

```java
boolean isMatch(String word, int index, TrieNode node) {
    if (index == word.length()) return node.isEnd;
    char c = word.charAt(index);
    if (c == '.') {
        for (TrieNode child : node.next) {
            if (child != null && isMatch(word, index + 1, child)) return true;
        }
        return false;
    }
    return node.next[c - 'a'] != null && isMatch(word, index + 1, node.next[c - 'a']);
}
```

#### Trie + DFS（二维网格搜索）

```java
void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
    char ch = board[r][c];
    TrieNode next = node.next[ch - 'a'];
    if (next == null) return; // Trie 中无后继，剪枝

    if (next.word != null) {
        result.add(next.word);
        next.word = null; // 去重
    }

    board[r][c] = '#'; // 原地标记已访问
    for (int[] dir : DIRS) {
        int nr = r + dir[0], nc = c + dir[1];
        if (inBounds(nr, nc) && board[nr][nc] != '#') {
            dfs(board, nr, nc, next, result);
        }
    }
    board[r][c] = ch; // 回退标记

    // 回退时剪枝：子节点无后继且非单词结尾 → 删除
    if (isEmpty(next) && next.word == null) {
        node.next[ch - 'a'] = null;
    }
}
```

#### 二进制 Trie（最大异或值）

```java
class BitTrie {
    int[][] next; // next[nodeId][0/1]
    int cnt = 0;
    int maxBitPos;

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
            int bit = (num >> i) & 1;
            int want = bit ^ 1; // 优先走相反方向
            if (next[curr][want] != 0) {
                xor |= (1 << i);
                curr = next[curr][want];
            } else {
                curr = next[curr][bit];
            }
        }
        return xor;
    }
}
```

#### 离线查询 + 二进制 Trie（带上界约束的最大异或）

```java
Arrays.sort(nums);
// queries 按 m 升序排序，保留原下标
int ptr = 0;
for (int[] q : sortedQueries) {
    int x = q[0], m = q[1], originalIdx = q[2];
    while (ptr < nums.length && nums[ptr] <= m) {
        trie.insert(nums[ptr++]);
    }
    ans[originalIdx] = (ptr == 0) ? -1 : x ^ trie.queryMaxXor(x);
}
```

### 关键理解

#### 1. 边存字符，节点存状态
Trie 中**边表示字符，节点表示状态**（`isEnd`、`val` 等）。处理字符时应先沿边移动到下一个节点，再在节点上做判断。反过来（在当前节点判断未知的边）会导致控制流出现回退，结构不线性。（`648` 的教训）

#### 2. 通配符匹配用纯递归
遇到 `'.'` 需要尝试所有子节点，混合"半迭代 + 半递归"可读性差。统一用递归更清晰。（`211`）

#### 3. Trie 节点的 val 可以设计为前缀和
`677` 中将 `val` 从"终点值"改为"路径上的累加值"，`sum(prefix)` 只需返回前缀终点节点的 `val`，省掉子树递归收集。插入时需要记录旧值来计算 delta。

#### 4. 找到答案后的 Trie 清理
`212` 中找到单词后将 `word = null` 去重，回退时如果子节点无后继且非单词结尾则删除。删除条件简洁，不需要维护"经过节点的单词计数"等额外信息。

#### 5. 二进制 Trie 从高位到低位
异或比较大小时高位优先，所以二进制 Trie 必须从最高有效位往低位建树和查询。每位贪心走相反分支（`bit ^ 1`），使该位异或结果为 1。

#### 6. 离线查询将约束转化为单调扩张
`1707` 中将 queries 按 `m` 排序，`nums` 也排序，随着 `m` 增大逐步将满足条件的 `nums` 加入 Trie。每个 `nums` 只入 Trie 一次，避免重复筛选。

---

## Part B：位运算

### 题型分类

#### 一、异或消除（Single Number 系列）
- 出现两次的数：全体异或，成对抵消（`136`）
- 出现三次的数：模 3 状态机，`ones` / `twos` 双变量（`137`）
- 两个只出现一次的数：全体异或 → 按任一差异位分组 → 组内分别异或（`260`）

#### 二、低位操作
- `n & (n - 1)`：消去最低位的 1（`191`、`231`）
- `n & -n`：提取最低位的 1（`260`）

#### 三、位掩码（Bitmask）
- 适用：集合状态压缩，判断集合是否相交
- 核心：每个元素映射到一个 bit，集合操作 = 位运算
- 代表题：`318`

#### 四、位递推（Bit DP）
- 适用：利用已知子问题的位信息推导当前值
- 代表题：`338`

### 代码模板

#### 异或消除（出现两次）

```java
int xor = 0;
for (int num : nums) xor ^= num;
return xor;
```

#### 模 3 状态机（出现三次）

```java
int ones = 0, twos = 0;
for (int num : nums) {
    ones = (ones ^ num) & ~twos;
    twos = (twos ^ num) & ~ones;
}
return ones;
```

状态流转（某一位上 num = 1 时）：`(ones, twos)` = `(0,0) → (1,0) → (0,1) → (0,0)`。  
`& ~twos` 的作用：`twos` 中为 1 的位禁止 `ones` 在该位更新。两行表达式结构对称。

#### 按差异位分组（两个 single number）

```java
int xor = 0;
for (int num : nums) xor ^= num;
int divider = xor & -xor; // 提取最低差异位
int a = 0;
for (int num : nums) {
    if ((num & divider) != 0) a ^= num;
}
return new int[]{a, xor ^ a};
```

#### 位掩码（集合判交）

```java
Map<Integer, Integer> maskToMaxLen = new HashMap<>();
for (String w : words) {
    int mask = 0;
    for (char c : w.toCharArray()) mask |= 1 << (c - 'a');
    maskToMaxLen.merge(mask, w.length(), Math::max);
}
// 枚举所有 mask 对，mask1 & mask2 == 0 表示无公共元素
```

#### 低位操作

```java
n & (n - 1)  // 消去最低位的 1（191: 计数循环；231: 结果为 0 则是 2 的幂）
n & -n       // 提取最低位的 1（260: 找分组依据）
(num >> i) & 1  // 取第 i 位
1 << i       // 第 i 位为 1 的值
```

#### 位递推

```java
// 338: ans[i] = ans[i >> 1] + (i & 1)
for (int i = 1; i <= n; i++) {
    ans[i] = ans[i >> 1] + (i & 1);
}
```

### 关键理解

#### 1. 异或 = 不进位的二进制加法
异或的本质是按位加法并丢弃进位。成对出现的数异或后抵消为 0，这是所有 Single Number 系列的基础。

#### 2. 模 k 消除的通用思路
当每个数出现 k 次、一个数出现 1 次时，需要构造模 k 的状态机。k = 2 时就是普通异或；k = 3 时需要 `ones` / `twos` 两个变量，通过互相屏蔽（`& ~`）实现三态循环。

#### 3. `n & (n - 1)` 和 `n & -n` 是位操作的基础积木
前者消去最低位的 1，后者提取最低位的 1。大量位运算题都建立在这两个操作之上。

#### 4. 位掩码用于集合状态压缩
当元素种类有限（如 26 个字母）时，可以将集合压缩为一个 int。集合操作变成位运算：并集 = `|`，交集 = `&`，判交 = `& == 0`。相同 mask 的元素只需保留最优值。

#### 5. 位递推利用"去掉最低位"的子问题关系
`i >> 1` 是 `i` 去掉最低位后的值，`ans[i >> 1]` 已知，当前 `i` 只多了最低位 `(i & 1)` 的贡献。

---

## 题型识别

| 题目特征 | 优先考虑 |
|---|---|
| 字符串前缀匹配 / 前缀查找 | Trie |
| 通配符匹配 | Trie + 递归 |
| 二维网格上搜索多个单词 | Trie + DFS |
| 最大异或值 | 二进制 Trie（高位贪心） |
| 带上界约束的异或查询 | 离线排序 + 二进制 Trie |
| 出现 k 次 / 1 次的数 | 异或 / 模 k 状态机 |
| 判断集合是否相交 | 位掩码 `&` |
| 计数 1 的个数 / 判断 2 的幂 | `n & (n-1)` |
| 利用子问题的位信息 | 位递推 |

---

## 常见坑

### Trie
1. 在当前节点判断未知边 → 控制流出现回退；应先移动再判断（`648`）
2. 找到单词后没有继续搜索 → 更长的单词可能以它为前缀（`212`）
3. 没有标记已访问格子 → 同一路径重复使用同一格（`212`）
4. `Trie` 和 `TrieNode` 职责混淆 → `maxBitPos` 等全局信息应放在 `Trie` 类上（`421`）

### 位运算
5. 模 3 状态机中 `ones` 和 `twos` 更新顺序不能颠倒 → `ones` 更新后的新值参与 `twos` 的屏蔽（`137`）
6. `n & -n` 对 `n = 0` 无意义 → 需要保证 `xor != 0`（`260`）
7. 位掩码中运算符优先级：`1 << c - 'a'` 实际是 `1 << (c - 'a')`（正确），但加括号可读性更好（`318`）
8. 位掩码不能按超集关系合并 → 超集能配对的对象是子集的子集，但子集可能有超集无法配对的更优对象（`318`）

---

## 面试话术

**Trie（字符串匹配）**
- Trie 的核心是相同前缀共享路径。插入和查找都是沿字符逐级往下走，时间复杂度与字符串长度成正比。需要区分"前缀存在"和"完整单词存在"时，用 `isEnd` 标记。

**二进制 Trie（最大异或）**
- 把所有数字按二进制位从高到低插入 Trie。查询某个数的最大异或对象时，每一位贪心走相反方向，因为异或中高位的 1 比低位所有位加起来都大。

**异或消除**
- 异或是不进位加法，成对出现的数会抵消。对于两个 single number 的情况，先全体异或得到两数的异或值，再用任意一个差异位把所有数分成两组，组内各自异或就能分离出两个答案。

**模 3 状态机**
- 用 `ones` 和 `twos` 两个变量模拟每一位出现次数对 3 取模的状态。每次新数进来，先用异或更新 `ones`，再用 `twos` 的屏蔽位阻止溢出到第三次；`twos` 的更新对称。最终 `ones` 就是只出现一次的数。
