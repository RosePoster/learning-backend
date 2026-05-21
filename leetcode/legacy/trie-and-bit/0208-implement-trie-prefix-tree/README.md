# 208. Implement Trie

## 我的思考
第一次接触 Trie。

直觉想到可以用类似链表的结构存储单词：
相同前缀共享路径，不同字符产生分叉，因此结构自然变成一棵树。

实现时最初设计为：char value + TreeNode[] child

后来考虑到：
数组顺序访问需要遍历，
因此改为：Map<Character, TreeNode>

进一步发现：
Map 的 key 已经表示字符，因此节点不再需要 `char value` 字段。

完成后注意到 `insert`、`search`、`startsWith` 都包含相同的遍历逻辑，
于是抽象出 `findNode` 方法统一处理。

如果不考虑拓展性，由于本题字符范围只有 `a-z`，
也可以使用 `TreeNode[26]` 实现，常数时间更优。

## 关键点
Trie 的核心是：
- 相同前缀共享路径
- 单词结束位置用 `isEnd` 标记

## 复杂度
- 时间：`O(n)`
- 空间：`O(total characters)`