# 437. Path Sum III

## 我的思考
本次复刷耗时 20min。

第一时间考虑是否需要记录前缀和。想到这题本质上是“一维数组子数组和”的二叉树版本，因此前缀和计数是必要的。确定使用前缀和后，在 DFS 中进入节点时加入当前前缀和，退出节点时回退，避免兄弟子树互相污染。

## 卡点
`currSum - targetSum` 第一时间写反成了 `targetSum - currSum`，说明等式没有仔细推导。

## 关键点
当前路径和满足：

`currSum - oldSum = targetSum`

所以需要查找：

`oldSum = currSum - targetSum`

`map` 只维护当前 root 到当前节点路径上的前缀和。进入节点时加入 `currSum`，递归左右子树后撤销 `currSum`。

## 主流解对照

## 复杂度
时间复杂度：O(n)  
空间复杂度：O(h)

## 注意
前缀和用 `long`，避免路径和溢出。