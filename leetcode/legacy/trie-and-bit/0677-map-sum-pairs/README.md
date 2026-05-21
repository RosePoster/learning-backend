# 677. Map Sum Pairs

## 我的思考
本题仍然可以直接使用 Trie 解决。

第一版思路比较直接：  
在 Trie 节点中存储 `val`，表示当前 key 对应的值。  
在 `sum(prefix)` 时先找到 prefix 对应的节点，然后递归遍历子树，将所有 `val` 累加。

注意到这里有一个性质：  
由于 `val = 0` 的累加不会影响结果，因此可以省略 `isEnd` 字段以及 `if(isEnd)` 的判断，直接累加所有节点的 `val`。

这样第一版的主要耗时在 `collect` 的递归收集过程。

进一步优化可以改变 `val` 的含义：  
将 `val` 设计为**前缀和**。在插入时，把当前 key 的增量（delta）累加到路径上的所有节点。

这样在 `sum(prefix)` 时，只需要找到 prefix 对应节点并返回其 `val`，即可省略 `collect` 的递归遍历。

## 卡点
无

## 关键点
- Trie 节点的 `val` 可以设计为前缀和，从而避免递归收集
- 插入时需要记录旧值，通过 `delta` 修正路径上的前缀和

## 复杂度
- 时间：`insert O(L)`，`sum O(L)`
- 空间：`O(total characters)`

## 注意
需要额外使用 `Map<String, Integer>` 记录 key 的旧值，否则更新 key 时无法正确维护前缀和。