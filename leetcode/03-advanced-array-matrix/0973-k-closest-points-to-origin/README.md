# 973. K Closest Points to Origin

## 我的思考
解法1，堆：

维护一个大小为 `k` 的大根堆，堆顶是当前保留点中距离最远的点。遍历 points 时加入当前点，如果堆大小超过 `k`，就弹出距离最远的点。最终堆中剩下的就是距离原点最近的 `k` 个点。

解法2，Quickselect：

目标不是完整排序，而是把距离最小的 `k` 个点放到数组前 `k` 个位置。

三向 partition 后数组结构为：

`[距离 < pivot][距离 == pivot][距离 > pivot]`

然后根据前 `k` 个点落在哪个区间，决定继续处理左侧、右侧，或者停止。

## 卡点
堆解法很快。

Quickselect 主要卡在三向 partition 还不够熟练，以及 `k` 的边界判断。

这里的 `k` 表示数量，不是下标。

## 关键点
Quickselect 中，partition 返回：

- `[begin, lt)`：距离小于 pivot；
- `[lt, gt]`：距离等于 pivot；
- `(gt, end]`：距离大于 pivot。

因为 `k` 表示前 `k` 个元素，所以停止条件是：

`lt <= k <= gt + 1`

如果：

- `k < lt`：前 `k` 个都在左侧，继续处理左边；
- `k > gt + 1`：左侧和中间还不够 `k` 个，继续处理右边；
- 否则：前 `k` 个已经确定，可以停止。

## 复杂度
堆解法：

时间复杂度：O(n log k)

空间复杂度：O(k)

Quickselect 解法：

时间复杂度：平均 O(n)，最坏 O(n^2)

空间复杂度：O(1)，不考虑返回数组；返回数组本身为 O(k)

## 注意
quickselect 边界： `target 是下标` 和 `target 是数量 k` 的区别。