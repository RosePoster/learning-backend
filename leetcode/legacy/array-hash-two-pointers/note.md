# 数组 / 哈希 / 双指针核心总结

## 题型分类

### 一、排序 + 双指针（kSum 系列）
- 适用：有序数组上的两数/三数/多数之和
- 核心思路：排序后，外层枚举固定 `k-2` 个数，最内层用双指针逼近目标
- 本质：利用有序性做剪枝，每一步稳定排除不可能解
- 代表题：`167`、`16`、`18`

### 二、读写双指针（原地操作）
- 适用：按段扫描 + 原地写回
- 核心思路：`read` 找完整段，`write` 写回结果，段结束后立即收集
- 代表题：`443`

### 三、哈希 / 频次数组
- 适用：统计出现次数、标记是否出现过、按值域建模
- 核心思路：先明确答案域（不是原始取值范围），再按答案域建数组
- 代表题：`36`、`274`

### 四、原地标记（In-place Marking）
- 适用：`1 <= nums[i] <= n`，要求 O(1) 额外空间
- 核心技巧：用正负号当 1-bit 标记，`abs()` 恢复原值，不丢下标含义
- 代表题：`442`

### 五、贪心状态维护
- 适用：判断某种结构存在性，且只需维护少量最优候选
- 核心思路：每次只保留对后续最有价值的候选值，用 `MAX_VALUE` 初始化规避非法状态
- 代表题：`334`

### 六、两遍扫描
- 适用：找左右边界、找破坏单调性的范围
- 核心思路：正向维护最大值找右边界，反向维护最小值找左边界
- 代表题：`581`

---

## 代码模板

### kSum 通用结构（排序 + 枚举 + 双指针）

2Sum、3Sum、4Sum 结构相同，区别仅在外层枚举的层数：

```java
Arrays.sort(nums);
// 外层：枚举前 k-2 个数（2Sum 无外层，3Sum 一层，4Sum 两层）
// 每层循环开头做去重：if (i > start && nums[i] == nums[i-1]) continue;
// 每层循环开头做剪枝（见下方"剪枝技巧"）

// 最内层：双指针找剩余两个数
int left = lastFixedIndex + 1, right = n - 1;
long restTarget = (long) target - fixedSum; // 用 long 防溢出
while (left < right) {
    int sum = nums[left] + nums[right]; // 两数相加不溢出（题设单值 <= 10^9）
    if (sum == restTarget) {
        // 收集答案
        // 跳过重复 left/right（见下方"去重写法"）
    } else if (sum < restTarget) {
        left++;
    } else {
        right--;
    }
}
```

### 读写双指针（按段处理）

```java
int write = 0, read = 0;
while (read < n) {
    char c = chars[read];
    int freq = 0;
    while (read < n && chars[read] == c) { read++; freq++; }
    chars[write++] = c;
    if (freq > 1) {
        for (char d : String.valueOf(freq).toCharArray()) {
            chars[write++] = d;
        }
    }
}
return write;
```

### 频次数组 + 答案域压缩（H-Index 类）

```java
int[] freq = new int[n + 1];
for (int c : citations) {
    freq[Math.min(c, n)]++; // 大于 n 的引用等价于 n
}
int total = 0;
for (int i = n; i >= 1; i--) {
    total += freq[i];
    if (total >= i) return i;
}
return 0;
```

### 多维布尔标记（Sudoku 类）

```java
boolean[][] row = new boolean[9][9];
boolean[][] col = new boolean[9][9];
boolean[][] box = new boolean[9][9];
// box 下标：(r / 3) * 3 + (c / 3)
```

### 原地标记（正负号，1 ≤ nums[i] ≤ n）

```java
for (int i = 0; i < n; i++) {
    int idx = Math.abs(nums[i]) - 1;
    if (nums[idx] < 0) {
        result.add(idx + 1); // 已标记过，说明重复
    } else {
        nums[idx] = -nums[idx]; // 首次访问，标记为负
    }
}
```

### 交换排序（Cycle Sort，1 ≤ nums[i] ≤ n）

```java
// 阶段一：归位
for (int i = 0; i < n; i++) {
    while (nums[i] != nums[nums[i] - 1]) {
        swap(nums, i, nums[i] - 1);
    }
}
// 阶段二：收集不在正确位置的数
for (int i = 0; i < n; i++) {
    if (nums[i] != i + 1) result.add(nums[i]);
}
```

### 两遍扫描（找最短待排序区间）

```java
int right = -1, max = Integer.MIN_VALUE;
for (int i = 0; i < n; i++) {
    if (nums[i] < max) right = i;
    else max = nums[i];
}
int left = n, min = Integer.MAX_VALUE;
for (int i = n - 1; i >= 0; i--) {
    if (nums[i] > min) left = i;
    else min = nums[i];
}
return right < left ? 0 : right - left + 1;
```

### 贪心状态维护（递增三元子序列）

```java
int oneNum = Integer.MAX_VALUE, twoNum = Integer.MAX_VALUE;
for (int num : nums) {
    if (num > twoNum) return true;
    else if (num > oneNum) twoNum = num;
    else oneNum = num;
}
return false;
```

---

## kSum 去重写法

```java
// 枚举层去重（每一层结构相同，start 是该层循环的起始下标）
if (i > start && nums[i] == nums[i - 1]) continue;

// 双指针命中后跳过重复值
while (left < right && nums[left] == nums[left + 1]) left++;
while (left < right && nums[right] == nums[right - 1]) right--;
left++; right--;
```

## kSum 剪枝技巧

以 4Sum 第一层为例，其他层同理：

```java
// 当前 i 搭配最小的三个数已超过 target → 后面只会更大，直接 break
if ((long) nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break;

// 当前 i 搭配最大的三个数仍小于 target → 这一轮 i 不可能有答案，continue
if ((long) nums[i] + nums[n-3] + nums[n-2] + nums[n-1] < target) continue;
```

---

## 题型识别

| 题目特征 | 优先考虑 |
|---|---|
| 有序数组 + 两数/三数之和 | 排序 + 双指针 |
| k 数之和（k > 2） | 排序 + (k-2) 层枚举 + 双指针 |
| 统计出现次数 / 判断是否出现 | 哈希表 / 布尔数组 |
| `1 <= nums[i] <= n`，要求 O(1) 空间 | 原地标记（正负号）/ 交换排序 |
| 判断某结构是否存在（递增子序列等） | 贪心维护最优候选 |
| 找最短/最长待排序区间 | 两遍扫描（正向找右界，反向找左界） |
| 按段扫描 + 原地写回 | 读写双指针 + `while` 找完整段 |

---

## 关键理解

### 1. 双指针的本质是有序性剪枝
每一步根据两端之和与 target 的关系，稳定排除一部分不可能解。小了推左端，大了推右端。

### 2. 答案域 ≠ 取值范围
`274` 中 H-index 最大是 `n`（论文总数），不是最大引用数。先确定答案域，再按答案域建模。

### 3. 调整阶段和收集阶段分离
交换排序类：先把所有元素归位，再统一收集不在正确位置的元素。两阶段职责清晰，不容易漏元素。

### 4. 正负号是免费的 1 bit
`1 <= nums[i] <= n` 时，用 `nums[val-1]` 的符号位记录 `val` 是否出现过。`abs()` 恢复原值，不丢映射关系。

### 5. 初始化选 MAX_VALUE 避免非法状态
候选状态未成立时用 `MAX_VALUE` 初始化，只有真正找到合法候选后才赋有效值。

### 6. 按段扫描用 while 找完整段
外层 `while` 起段头、内层 `while` 找段尾、统计完立即写入。避免 `for` 写法末尾需要额外收尾。

### 7. 先问题目真正要什么结构信息
`581` 不需要恢复排序过程，只需要定位待排序区间的左右边界。建模前先确认目标，避免过度模拟。

---

## 常见坑

1. kSum 忘记去重 → 排序后，每层枚举开头跳过相邻相等值
2. 多数相减溢出 → `restTarget = (long) target - nums[i] - nums[j]`，用 `long`
3. 交换排序边交换边收集 → 容易漏元素；先归位、再收集更稳
4. 只看取值范围，没看答案域 → 数组开太大或漏掉压缩机会
5. 候选状态未成立就被比较 → 用 `MAX_VALUE` 初始化
6. 按段扫描用 `for` + 末尾收尾 → 改用 `while` 找完整段后立即写入

---

## 面试话术

**双指针（有序两数之和）**
- 排序后左右各一个指针。当前和偏大就右端左移，偏小就左端右移。每步排除一种不可能，总体 O(n)。

**kSum 扩展**
- k 数之和的通用做法：排序后外层枚举前 k-2 个数，最内层双指针。每层做去重和剪枝，整体 O(n^(k-1))。

**原地标记**
- 利用 `1 <= nums[i] <= n`，把每个值映射到对应下标，用正负号记录是否出现过。不需要额外空间。

**答案域分析（H-index）**
- H-index 最大只能是 n。频次数组只开 n+1，从大到小累加，第一个满足"累计论文数 >= i"的 i 就是答案。
