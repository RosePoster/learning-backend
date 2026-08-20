# 专题 · 数组 / 哈希 / 双指针

> 类型：专题总结 ｜ 来源：旧 Round 6

## 摘要

六类题型：kSum 双指针、读写双指针、频次数组、原地标记、贪心状态维护、两遍扫描。双指针的本质是有序性剪枝；哈希建模的关键是先确定答案域而非取值范围。

## 题型分类

| 类型 | 适用 | 核心 | 代表题 |
|---|---|---|---|
| 排序 + 双指针（kSum） | 有序数组多数之和 | 外层枚举 k-2 个数，内层双指针逼近；有序性剪枝 | 0167 / 0016 / 0018 |
| 读写双指针 | 按段扫描 + 原地写回 | read 找完整段，write 写回，段结束立即收集 | 0443 |
| 哈希 / 频次数组 | 计数、存在性、值域建模 | 先明确答案域，按答案域建数组 | 0036 / 0274 |
| 原地标记 | `1 ≤ nums[i] ≤ n` + O(1) 空间 | 正负号当 1-bit 标记，`abs()` 恢复原值 | 0442 |
| 贪心状态维护 | 结构存在性判断 | 只保留对后续最有价值的候选，`MAX_VALUE` 初始化 | 0334 |
| 两遍扫描 | 找左右边界 | 正向维护最大值找右界，反向维护最小值找左界 | 0581 |

## 模板

kSum 通用结构（2/3/4Sum 结构相同，仅外层枚举层数不同）：

```java
Arrays.sort(nums);
// 每层枚举开头去重：if (i > start && nums[i] == nums[i-1]) continue;
// 每层枚举开头剪枝（见下）
int left = lastFixedIndex + 1, right = n - 1;
long restTarget = (long) target - fixedSum; // long 防溢出
while (left < right) {
    int sum = nums[left] + nums[right];
    if (sum == restTarget) {
        // 收集答案 + 跳过重复
        while (left < right && nums[left] == nums[left + 1]) left++;
        while (left < right && nums[right] == nums[right - 1]) right--;
        left++; right--;
    } else if (sum < restTarget) left++;
    else right--;
}
```

kSum 剪枝（以 4Sum 第一层为例）：

```java
if ((long) nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break;    // 最小组合已超
if ((long) nums[i] + nums[n-3] + nums[n-2] + nums[n-1] < target) continue; // 最大组合仍小
```

读写双指针（按段处理）：

```java
int write = 0, read = 0;
while (read < n) {
    char c = chars[read];
    int freq = 0;
    while (read < n && chars[read] == c) { read++; freq++; }
    chars[write++] = c;
    if (freq > 1) for (char d : String.valueOf(freq).toCharArray()) chars[write++] = d;
}
return write;
```

频次数组 + 答案域压缩（H-Index 类）：

```java
int[] freq = new int[n + 1];
for (int c : citations) freq[Math.min(c, n)]++; // 大于 n 的引用等价于 n
int total = 0;
for (int i = n; i >= 1; i--) {
    total += freq[i];
    if (total >= i) return i;
}
return 0;
```

原地标记（正负号）：

```java
for (int i = 0; i < n; i++) {
    int idx = Math.abs(nums[i]) - 1;
    if (nums[idx] < 0) result.add(idx + 1); // 已标记，重复
    else nums[idx] = -nums[idx];
}
```

交换排序（Cycle Sort，先归位再收集）：

```java
for (int i = 0; i < n; i++)
    while (nums[i] != nums[nums[i] - 1]) swap(nums, i, nums[i] - 1);
for (int i = 0; i < n; i++)
    if (nums[i] != i + 1) result.add(nums[i]);
```

两遍扫描（最短待排序区间）：

```java
int right = -1, max = Integer.MIN_VALUE;
for (int i = 0; i < n; i++) {
    if (nums[i] < max) right = i; else max = nums[i];
}
int left = n, min = Integer.MAX_VALUE;
for (int i = n - 1; i >= 0; i--) {
    if (nums[i] > min) left = i; else min = nums[i];
}
return right < left ? 0 : right - left + 1;
```

贪心状态维护（递增三元组）：

```java
int oneNum = Integer.MAX_VALUE, twoNum = Integer.MAX_VALUE;
for (int num : nums) {
    if (num > twoNum) return true;
    else if (num > oneNum) twoNum = num;
    else oneNum = num;
}
return false;
```

多维布尔标记（Sudoku 类）：`row[9][9] / col[9][9] / box[9][9]`，box 下标 `(r/3)*3 + c/3`。

## 关键理解

1. 双指针的本质是有序性剪枝：每步稳定排除一部分不可能解。
2. 答案域 ≠ 取值范围（0274：H-index 上限是 n，不是最大引用数）。
3. 调整阶段和收集阶段分离（交换排序先全部归位再统一收集）。
4. 正负号是免费的 1 bit（前提 `1 ≤ nums[i] ≤ n`）。
5. 候选状态未成立时用 `MAX_VALUE` 初始化，规避非法比较。
6. 按段扫描用 while 找完整段，避免 for 写法的末尾收尾。
7. 建模前先确认题目要的结构信息（0581 只需边界，不需恢复排序过程）。

## 题型识别

| 特征 | 优先考虑 |
|---|---|
| 有序数组 + 两数/三数之和 | 排序 + 双指针 |
| k 数之和（k > 2） | 排序 + (k-2) 层枚举 + 双指针 |
| 统计次数 / 存在性 | 哈希表 / 布尔数组 |
| `1 ≤ nums[i] ≤ n` + O(1) 空间 | 原地标记 / 交换排序 |
| 结构存在性（递增子序列等） | 贪心维护最优候选 |
| 最短/最长待处理区间 | 两遍扫描 |
| 按段扫描 + 原地写回 | 读写双指针 |

## 常见坑

1. kSum 忘去重 → 每层枚举开头跳过相邻相等值。
2. 多数相减溢出 → `restTarget` 用 long。
3. 交换排序边交换边收集 → 先归位、再收集。
4. 只看取值范围不看答案域 → 数组开太大或漏掉压缩。
5. 候选未成立就参与比较 → `MAX_VALUE` 初始化。

## 面试表述

双指针（有序两数之和）：排序后左右指针，和偏大右端左移、偏小左端右移，每步排除一类不可能，整体 O(n)。kSum：外层枚举前 k-2 个数 + 内层双指针，每层去重与剪枝，整体 O(n^(k-1))。原地标记：值映射到下标，用符号位记录出现，O(1) 空间。答案域分析（H-Index）：答案上限为 n，频次数组开 n+1，从大到小累加找第一个满足"累计 ≥ i"的 i。
