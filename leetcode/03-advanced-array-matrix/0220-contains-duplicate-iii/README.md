# 220. Contains Duplicate III

## 我的思考
题目可以建模为固定大小滑动窗口。

对每个 `nums[i]`，只需要在最近 `indexDiff` 个元素中判断，是否存在某个旧元素落在：

`[nums[i] - valueDiff, nums[i] + valueDiff]`

因此需要一个数据结构支持：

- 快速查询某个范围内是否存在元素；
- 快速插入当前元素；
- 快速删除滑动窗口左端过期元素。

一开始想到暴力遍历窗口，复杂度为 `O(n * indexDiff)`，最坏会退化到 `O(n^2)`，不能接受。

后来意识到这类“动态有序集合 + 范围查询”可以用红黑树，也就是 Java 的 `TreeMap / TreeSet`。

## 卡点
卡点不是滑动窗口，而是不知道算法题中可以直接调用红黑树结构。

需要补进工具箱：

动态维护一组数，并查询某个范围内是否存在元素时，优先想到：

`TreeSet / TreeMap`

`ceilingKey / floorKey`

## 关键点
维护一个大小不超过 `indexDiff` 的滑动窗口。

对当前值 `x`，查询窗口中是否存在第一个大于等于 `x - valueDiff` 的数：

`ceil = map.ceilingKey(x - valueDiff)`

如果：

`ceil != null && ceil <= x + valueDiff`

说明窗口中存在合法元素，返回 `true`。

## 复杂度
时间复杂度：O(n log indexDiff)

空间复杂度：O(indexDiff)

## 注意
map.ceilingKey()可能返回null。

由于数值可能溢出 int，计算边界时需要转为 `long`。

如果使用 `TreeMap<Long, Integer>`，可以处理重复元素。删除窗口左端元素时，需要根据计数决定是减一还是移除 key。
