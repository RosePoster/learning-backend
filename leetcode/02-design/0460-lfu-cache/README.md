# 460. LFU Cache

## 我的思考

本题耗时约 1h。

思路本身很快，约 5min 就确定核心方向：  
需要同时维护 key 到 node 的映射，以及 freq 到同频节点集合的映射。

最终结构：

- `valMap`：维护 `key -> Node`
- `freqMap`：维护 `freq -> DoublyLinkedList`
- `Node`：存储 `key / val / freq / prev / next`
- 每个 `DoublyLinkedList` 维护一个频率桶，同频节点内部按 LRU 顺序排列
- `minFreq` 记录当前最小频率，用于容量满时 O(1) 找到要淘汰的频率桶

## 结构选择过程

一开始用了 `freqMap`。

后来发现不太好找 `minFreq`，于是尝试给 `DoublyLinkedList` 加前后指针，把不同频率桶也串成链表。

写到一半发现，如果频率桶自己能前后连接，好像就不需要 `freqMap` 了，于是删掉 `freqMap`。

但继续写又发现：删除或更新一个 node 时，不好快速找到它所属的 bucket，于是尝试给 node 加一个指向 bucket 的指针。

这样虽然理论上可行，但指针关系变得复杂，代码风险明显上升。

最终基于一个关键发现：  
`minFreq` 只会在两种情况下变化：

1. 当前最小频率桶被清空时，`minFreq++`
2. 插入新节点时，`minFreq` 重置为 1

因此不需要维护频率桶之间的链表。退回 `freqMap` 结构即可。

## 关键点

LFU 要同时处理两个维度：

1. 频率维度：优先淘汰访问次数最少的节点
2. 时间维度：如果频率相同，淘汰最久未使用的节点

因此不能只用一个普通链表。

标准结构是：

- `HashMap<Integer, Node>` 负责 O(1) 根据 key 找到节点
- `HashMap<Integer, DoublyLinkedList>` 负责 O(1) 找到某个频率桶
- 每个频率桶内部用双向链表维护同频节点的 LRU 顺序
- `minFreq` 负责 O(1) 定位当前最低频率桶

## 操作逻辑

### get

1. 通过 `valMap` 找到节点
2. 如果不存在，返回 -1
3. 从当前频率桶中删除该节点
4. 节点 `freq++`
5. 插入到新频率桶头部
6. 返回节点 value

### put

如果 key 已存在：

1. 更新 value
2. 等效执行一次访问逻辑，即删除旧频率桶中的节点，再插入到 `freq + 1` 桶

如果 key 不存在：

1. 如果容量已满，删除 `minFreq` 对应频率桶中的尾部节点
2. 新建频率为 0 的节点
3. 插入后通过统一逻辑更新为频率 1
4. `minFreq` 重置为 1

## Trade-off

当前版本中，某些中间状态下可能会短暂出现 `minFreq` 指向空 bucket 的情况。

例如删除当前 `minFreq` bucket 中最后一个节点后，`removeNode` 会执行 `minFreq++`。  
但如果后续是插入新节点，最终 `updateAndInsertNode` 又会通过 `freq == 1` 将 `minFreq` 重置为 1。

因此最终状态不会错，但中间状态不够干净。

如果要修复这个异味，可以重新安排流程，让 `removeNode` 只负责删除节点和清理空桶，不在所有场景下都直接修改 `minFreq`；由上层逻辑根据 get / put / eviction 的语义决定如何更新 `minFreq`。

但考虑到面试时间有限，当前写法可以先接受。

## 复杂度

- `get`：O(1)
- `put`：O(1)
- 空间：O(capacity)

## 复盘

这题真正难点不在代码量，而在数据结构组织。

一开始想用单链表或总链表维护频率顺序，会遇到更新频率时需要跨节点移动的问题，无法保证 O(1)。

正确抽象是把 LFU 拆成两层：

- 第一层：key 到 node
- 第二层：freq 到同频 LRU 链表

`minFreq` 的维护是本题核心细节。  
关键发现是：`minFreq` 不需要通过全局扫描获得，它只会在最低频桶被清空时递增，或者在插入新节点时重置为 1。