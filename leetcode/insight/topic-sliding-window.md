# 专题 · 滑动窗口

> 类型：专题总结 ｜ 来源：旧 Round 5

## 摘要

滑动窗口的本质是维护一个连续区间的不变量，并在移动中高效更新答案。三个模板按"窗口长度是否固定、求最值还是计数"选择；进阶点是弱不变量（松维护）的使用条件。

## 模板选择

| 模板 | 特征 | 代表题 |
|---|---|---|
| 一：固定长度窗口 | 窗口大小天然固定（k 或 pattern 长度），right 扩张、left 同步跟进 | 0643 / 1456 / 0567 |
| 二：变长窗口求最值 | right 持续扩张，违反约束时 left 收缩到恢复合法 | 1004 / 0424 / 1208 |
| 三：变长窗口做计数 | 核心是"当前 right 结尾新增多少答案"，合法后批量统计 | 0713 / 0992 |

区分：长度天然不变 → 固定窗口；约束是"最多 / 至多 / 不超过" → 变长窗口；问子数组个数 → 除维护合法窗口外还要设计答案收集。

## 收集答案

- 最值类：窗口恢复合法后用 `right - left + 1` 更新。
- 计数类：窗口内任意起点都合法时 `ans += right - left + 1`。
- 恰好 K 类：优先转化 `exactly(k) = atMost(k) - atMost(k-1)`，比直接维护"恰好 K"更稳。

## 不变量设计

先想清楚四件事：窗口维护的量是什么；什么条件下合法；right 扩张后 left 收缩到什么程度；答案是"合法时更新"还是"统计本轮新增"。示例：1004 窗口内 0 的个数 ≤ k；1208 总代价 ≤ maxCost；0424 窗口长 − maxCount ≤ k；0992 atMostK 不同整数 ≤ k。

## 关键理解：弱不变量（松维护）

1. 辅助量不一定要精确回退：0424 的 `maxCount` 可以是历史最大值而非当前窗口真实最大值。
2. 严格合法不是唯一目标：窗口可维护成"仍有成为答案的潜力"（1208）。
3. 使用条件：即使辅助量不精确，窗口判定也不会漏掉最优答案、不影响最终正确性。
4. 计数题优先看每个 right 的贡献，而非只盯一个合法窗口（0713 / 0992）。

相关复盘见 [Round 4 · 主线二](round-04-audit-r1-r12.md)：0424 复刷时误维护了可真实回退的 maxFreq，属于"弱状态够用却维护强状态"。

## 模板

```java
// 固定长度
for (int right = 0; right < n; right++) {
    add(nums[right]);
    if (right >= k) remove(nums[right - k]);
    if (right >= k - 1) updateAnswer();
}

// 变长求最值
int left = 0;
for (int right = 0; right < n; right++) {
    add(nums[right]);
    while (!valid()) remove(nums[left++]);
    updateAnswer(right - left + 1);
}

// 变长计数（at most）
int left = 0, ans = 0;
for (int right = 0; right < n; right++) {
    add(nums[right]);
    while (!valid()) remove(nums[left++]);
    ans += right - left + 1;
}

// 恰好 K
int exactlyK(int[] nums, int k) { return atMost(nums, k) - atMost(nums, k - 1); }
```

## 题型识别

- 子串 / 子数组且要求连续 → 滑动窗口。
- 最长 / 最大 / 最短的连续区间 → 变长窗口。
- 长度固定的连续区间统计 → 固定窗口。
- 子数组个数统计 → 每个 right 的贡献。
- 恰好 k 个 → 转 atMost。
- 元素可能为负且约束基于和 → 窗口无单调性，滑动窗口不适用（0560 用前缀和）。

## 常见坑

1. 未先判断窗口长度是否固定，定长题写成变长。
2. 只会维护合法窗口，不会统计同一 right 的答案数。
3. exactly k 没想到转 atMost 差分。
4. 辅助变量语义不清（unsatisfied / diffNums / redundantPre 类）。
5. 默认所有辅助量必须精确反映当前窗口，思路变重。
6. 收缩条件必须是"违反约束就收缩"，不能凭感觉。

## 面试表述

维护连续窗口 `[left, right]`：right 负责扩张并纳入统计；违反约束时移动 left 收缩至恢复不变量；求最值则在窗口合法时更新答案，求个数则统计当前 right 结尾新增的合法子数组数。
