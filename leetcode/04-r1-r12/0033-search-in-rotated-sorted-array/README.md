# 33. Search in Rotated Sorted Array

## 我的思考
本次复刷耗时 12min。主要处理 `leftNum / midNum / rightNum` 三者的条件判断。

先用 `midNum >= leftNum` 判断左侧是否有序，作为上层判断；在确定左侧有序后，再用 `target < leftNum || target > midNum` 判断 target 是否不在有序侧，若不在则交给另一侧兜底。定好一边后，另一边镜像处理。

## 卡点
条件分层：先判断有序性，再判断 target 是否落在有序侧。

## 关键点
旋转数组二分不要直接分析非有序侧。每轮只和确定有序的一侧交互，target 不在有序侧时，用另一侧兜底。

## 主流解对照

## 复杂度
时间复杂度：O(log n)  
空间复杂度：O(1)

## 注意
LeetCode 33 无重复元素，`midNum >= leftNum` 可以稳定判断左侧有序。