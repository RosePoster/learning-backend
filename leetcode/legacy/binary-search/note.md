# 二分法经验总结

## 模板选择

### 模板一：`while(begin <= end)`
- 搜索空间是**具体的值**，找到直接 return
- `begin = mid + 1`，`end = mid - 1`
- 循环结束意味着没找到
- 适用：查找某个具体元素是否存在
- 最终会找到一个值或者区间为空

### 模板二：`while(begin < end)`
- 搜索空间是**一个区间**，最终收敛到一个点
- `begin = mid + 1`，`end = mid`（安全组合，不会死循环）
- 循环结束时 `begin == end`，就是答案
- 适用：找左边界、找满足条件的最小/最大值
- 最终区间收敛到一个点

---

## mid 计算与收缩方向

```java
int mid = begin + (end - begin) / 2;      // 下取整（默认）
int mid = begin + (end - begin + 1) / 2;  // 上取整，当 begin = mid 时必须用
```

| begin 收缩 | end 收缩 | mid 取整 |
|---|---|---|
| `begin = mid + 1` | `end = mid` | 下取整（默认） |
| `begin = mid` | `end = mid - 1` | **上取整** |

`begin = mid + 1` 搭配 `end = mid` 是最常用、最安全的组合。  
`begin = mid` 搭配下取整会死循环，必须上取整。

---

## 不变量设计
二分的本质是维护一个**循环不变量**，每次缩小区间时不变量必须成立。
- 540题：不变量是"begin 到 mid 是完整的若干 pair"
- 设计好不变量，边界条件自然清晰

---

## 常见坑
1. `mid - 1` / `mid + 1` 越界：使用前确认区间长度
2. 死循环：`begin = mid` 时必须上取整
3. 最终答案是 `begin` 还是 `end`：`while(begin < end)` 结束时二者相等，取哪个都行；`while(begin <= end)` 结束时需要确认

---

## 题型识别与本质
- 二分的本质不是”在有序数组里找数”，而是在一个有单调性的判定空间里找边界
- 数组有序 / 具有单调性 → 二分
- 求满足条件的最小值/最大值 → 二分答案
- 搜索空间可以折半排除 → 二分（这一点容易忘记）

每次二分时问自己：
1. 搜索空间是什么？
2. 单调性是什么？
3. 要找的是哪个边界？
4. mid 是否需要保留？

## 面试解释算法思路
- 我二分的不是元素本身，而是一个满足单调性的搜索空间。
- 目标是找某个边界：第一个满足条件的位置 / 最后一个满足条件的位置。
- 我维护的区间不变量是：答案始终在 [begin, end] 中。
- 每次根据 mid 是否可能成为答案，决定保留 mid 还是丢弃 mid。

## 模板
- 找第一个满足条件的位置
```java
int begin = 0, end = n - 1;
while (begin < end) {
    int mid = begin + (end - begin) / 2;
    if (cond(mid)) {
        end = mid;
    } else {
        begin = mid + 1;
    }
}
return begin;
```

- 找最后一个满足条件的位置
```java
int begin = 0, end = n - 1;
while (begin < end) {
    int mid = begin + (end - begin + 1) / 2;
    if (cond(mid)) {
        begin = mid;
    } else {
        end = mid - 1;
    }
}
return begin;
```