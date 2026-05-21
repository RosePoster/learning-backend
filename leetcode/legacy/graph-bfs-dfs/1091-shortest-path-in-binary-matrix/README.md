# 1091. Shortest Path in Binary Matrix

## 我的思考
本题思路比较直白，看到题目后可以直接锁定为无权图最短路径问题。

## 卡点
一开始在分层 BFS 中写成了：

`int[] curr = queue.poll();`
`int len = queue.size();`
`for(int index = 0; index < len; index++) { ... }`

这会导致层边界错误。因为 `queue.size()` 应该表示当前层开始时的节点数量，如果先 `poll()`，当前层节点数量已经被改变，后续循环就无法正确处理完整的一层。

正确顺序应当固定为：

1. 先记录当前层大小：`int len = queue.size();`
2. 再在 `for` 循环内部取出节点：`int[] curr = queue.poll();`

这个细节应该形成肌肉记忆，而不是依赖临场推理。

## 关键点
- 分层 BFS 中，必须先取 `queue.size()`，再在循环中 `poll()`。
- 访问过的格子可以直接原地标记为 `1`，复用输入数组作为 visited。
- 起点和终点被阻塞时，直接返回 `-1`。
- `n == 1` 时，起点即终点，返回 `1`。
- BFS 第一次到达终点时，对应的路径长度就是最短路径长度。

## 复杂度
- 时间：O(n²)，每个格子最多入队和出队一次，每次检查 8 个方向。
- 空间：O(n²)，最坏情况下队列可能存储 O(n²) 个格子。

## 注意
- 分层 BFS 固定模板：

  先 `int size = queue.size();`

  再：

  `for (...) { curr = queue.poll(); ... }`