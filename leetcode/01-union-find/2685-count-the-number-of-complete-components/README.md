# 2685. 统计完全连通分量的数量

## 我的思考
识别为连通分量统计题：先用 Union-Find 合并所有边，并在合并过程中维护每个 component 的节点数和边数。最后判断每个 component 是否满足完全图边数公式。

## 卡点
无。

## 关键点
每个连通分量若有 k 个节点，则完全图应有 k * (k - 1) / 2 条边。Union-Find 合并时同步维护 component edge count：同 component 内的边使边数加 1，不同 component 合并时边数为两边原边数加当前边。

## 复杂度
时间：O((n + e) α(n))
空间：O(n)

## 注意
本题属于 component summary 模式：连通分量不仅需要知道 root 和 size，还可以维护边数等摘要信息，用于最终判断结构性质。