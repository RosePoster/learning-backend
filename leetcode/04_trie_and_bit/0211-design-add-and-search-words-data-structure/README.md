# 211. Design Add and Search Words Data Structure

## 我的思考
本题核心是**多字符串匹配问题**，这类问题很自然可以联想到 Trie。

想到使用 Trie 之后，后续实现就比较直接了。

实现 `search` 时需要处理通配符 `'.'`，因此使用递归进行匹配。

## 卡点
最开始按照自然思路写出的 `search` 是**混合流**：  
在 `for` 遍历中，如果发现字符是 `'.'` 就进入递归函数。  
这种“半迭代 + 半递归”的逻辑阅读起来比较混乱，因此重构为**纯递归流程**。

## 关键点
1. 最初写的是：

   ```java
   if(nextNode != null && searchWorker(word, begin + 1, nextNode) == true)
   ```

   这里的 `== true` 是冗余的。  
   当时这样写是为了语义更清晰，因为 `searchWorker()` 的返回值不够直观。

   更好的做法是**优化函数命名**，将 `searchWorker` 改为 `isMatch`，  
   这样判断可以直接写为：

   ```java
   if(nextNode != null && isMatch(word, begin + 1, nextNode))
   ```

## 复杂度
- 时间：最坏 `O(26^L)`
- 空间：`O(L)`

## 注意
我习惯在 `void` 方法结尾写一个 `return`，这是一个不太好的习惯，需要改正。