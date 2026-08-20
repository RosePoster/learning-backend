# 专题 · 二分法

> 类型：专题总结 ｜ 来源：旧 Round 1

## 摘要

二分的本质不是"在有序数组里找数"，而是在具有单调性的判定空间里找边界。核心是维护循环不变量：答案始终在 `[begin, end]` 中。

## 模板选择

**模板一：`while (begin <= end)`** — 搜索空间是具体的值，找到直接 return；`begin = mid + 1`，`end = mid - 1`；循环结束意味着没找到。适用：查找某个具体元素是否存在。

**模板二：`while (begin < end)`** — 搜索空间是一个区间，最终收敛到一点；`begin = mid + 1`，`end = mid`（安全组合，不死循环）；结束时 `begin == end` 即答案。适用：找左边界、找满足条件的最小/最大值。

## mid 计算与收缩方向

```java
int mid = begin + (end - begin) / 2;      // 下取整（默认）
int mid = begin + (end - begin + 1) / 2;  // 上取整，当 begin = mid 时必须用
```

| begin 收缩 | end 收缩 | mid 取整 |
|---|---|---|
| `begin = mid + 1` | `end = mid` | 下取整（默认） |
| `begin = mid` | `end = mid - 1` | **上取整** |

`begin = mid` 搭配下取整会死循环，必须上取整。

## 不变量设计

每次缩小区间时不变量必须成立（如 0540：`begin` 到 `mid` 是完整的若干 pair）。不变量设计好，边界条件自然清晰。起手四问：

1. 搜索空间是什么？
2. 单调性是什么？
3. 要找的是哪个边界？
4. mid 是否需要保留？

## 模板

找第一个满足条件的位置：

```java
int begin = 0, end = n - 1;
while (begin < end) {
    int mid = begin + (end - begin) / 2;
    if (cond(mid)) end = mid;
    else begin = mid + 1;
}
return begin;
```

找最后一个满足条件的位置：

```java
int begin = 0, end = n - 1;
while (begin < end) {
    int mid = begin + (end - begin + 1) / 2;
    if (cond(mid)) begin = mid;
    else end = mid - 1;
}
return begin;
```

## 题型识别

- 数组有序 / 具有单调性 → 二分。
- 求满足条件的最小值/最大值 → 二分答案。
- 搜索空间可以折半排除 → 二分（最易被忽略的一类）。

## 常见坑

1. `mid ± 1` 越界：使用前确认区间长度。
2. 死循环：`begin = mid` 时必须上取整。
3. 最终答案取 `begin` 还是 `end`：`while (begin < end)` 结束时二者相等，取哪个都行；`while (begin <= end)` 需单独确认。

## 面试表述

二分的对象不是元素本身，而是一个满足单调性的搜索空间；目标是某个边界（第一个 / 最后一个满足条件的位置）；维护的不变量是"答案始终在 [begin, end] 中"；每轮根据 mid 是否可能成为答案，决定保留或丢弃 mid。
