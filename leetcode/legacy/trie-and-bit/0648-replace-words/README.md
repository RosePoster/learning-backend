# 648. Replace Words

## 我的思考
这题本质仍然是字符串匹配问题，可以直接使用 Trie 模板解决。

最初实现时，主循环的控制流出现了**回退逻辑**。当匹配到根节点时，先判断当前节点状态，再决定是否继续或回退，例如：

```java
for(int i = 0; i < sentence.length(); i++) {
    char c = sentence.charAt(i);
    sb.append(c);

    if(c == ' ') {
        curr = trieRoot;
        continue;
    }

    if(curr.isEnd) {
        i = continueRest(i, sentence);
        sb.deleteCharAt(sb.length() - 1);
        continue;
    }

    if(curr.next[c - 'a'] == null) {
        i = addRest(i, sentence, sb);
        continue;
    }

    curr = curr.next[c - 'a'];
}
```

这种写法的问题是：在**当前节点就开始做判断**，导致逻辑出现回退和多处分支，结构不够线性。

重新思考 Trie 的逻辑后发现：

Trie 的**边表示字符**，而**节点表示状态判断（如 isEnd）**。

因此处理流程应该是：

1. 先顺着字符对应的边移动到下一个节点
2. 再根据节点状态进行判断

而不是站在原节点上对未知的边做判断。

理清这一点后，主循环可以写成**严格线性的流程，没有回退逻辑**。

## 卡点
最初没有先移动到下一个节点再判断，而是在当前节点进行条件判断，导致控制流复杂。

## 关键点
- Trie 中 **边存储字符，节点存储状态**
- 处理字符时应先沿边移动，再在节点上做判断
- 这样可以保持主循环结构简单、线性

## 复杂度
- 时间：O(n)
- 空间：O(字典总字符数)

## 注意
无