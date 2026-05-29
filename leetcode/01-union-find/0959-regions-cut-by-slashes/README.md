# 959. 由斜杠划分区域

## 我的思考

看到题目后，直接想到左右斜杠会把一个格子切分成两个半区，因此可以把每个格子拆成 A/B 两个 part，再根据斜杠方向和相邻格子的接触关系进行 union。最终 Union-Find 中的连通分量数量就是区域数量。

本题的核心建模几乎没有消耗太多时间。主要耗时在实现结构的权衡上。

一开始尝试把 parent 写成 int[n][n][2]，让 Union-Find 直接理解二维格子和两个半区。但这样会导致 find 内部反复处理 (i, j, part) 到 index 的翻译，路径压缩也不容易保持简洁。

随后尝试写成 int[n * n][2]，但 find 仍然很难写成标准形式：

return parent[i] == i ? i : (parent[i] = find(parent[i]));

最终决定采用最朴素的一维 Union-Find，将每个半区都映射成一维 id。offset 和 index 转换全部放在外部处理，Union-Find 只负责 find 和 union。

在当前实现中，每个格子拆成两个节点：

leftPart = i * n + j  
rightPart = leftPart + n * n  

其中 leftPart 表示接触左边界的半区，rightPart 表示接触右边界的半区。左右相邻格子的连接固定成立；上下相邻格子的连接需要根据当前格子和邻居格子的斜杠方向判断。
空格内部没有分割，union(leftPart, rightPart)即可。

## 卡点

主要卡点不在题目建模，而在 Union-Find 的节点表示方式。

尝试让 parent 数组直接承载二维或三维业务语义时，会导致 find 和路径压缩变复杂。最后意识到，Union-Find 最适合处理一维整数节点，复杂坐标应该在外部统一映射成 id。

## 关键点

对于 '/'：

leftPart 接触左边界和上边界。  
rightPart 接触右边界和下边界。  

对于 '\'：

leftPart 接触左边界和下边界。  
rightPart 接触右边界和上边界。  

对于空格：

leftPart 和 rightPart 内部连通。  

相邻格子的边界接触关系需要 union：

当前格子的 leftPart 与左侧格子的 rightPart 连通。  
当前格子的 rightPart 与右侧格子的 leftPart 连通。  
当前格子的上边界 part 与上方格子的下边界 part 连通。  
当前格子的下边界 part 与下方格子的上边界 part 连通。  

这题也可以用 DFS/BFS 遍历做，本质是把每个半区看作图节点，显式遍历半区图并统计连通块数量。复杂度同样是 O(n²)。也可以使用 3x grid 放大法，把斜杠当作墙，再 flood fill 空白区域。3x 方法常数更大，但更直观、更容易 debug。

## 主流解对照

主流做法通常是将每个格子拆成 4 个小三角，根据 '/', '\', 空格处理格子内部 union，再连接相邻格子的对应三角。另一种常见做法是将原网格放大为 3x3，把斜杠标记为墙，再用 DFS/BFS 统计空白连通块。
本解法是对 Union-Find 建模的压缩变体，每格只拆成 2 个半区，节点更少。上下边界映射的推导和解释成本更高。

## 复杂度

时间：O(n² α(n²))，每个格子进行常数次 union，近似 O(n²)。

空间：O(n²)，每个格子拆成两个 Union-Find 节点，parent、size 数组均为 O(n²)。

如果使用 2-part DFS/BFS，时间和空间也都是 O(n²)。

如果使用 3x grid flood fill，时间和空间为 O((3n)²)，仍然是 O(n²)，但常数更大。

## 注意

UnionFind 永远建立在一维整数域 {0..m-1} 上。所有 (i,j,side)→id 的编码/解码逻辑放在调用方,绝不进 UF 内部。