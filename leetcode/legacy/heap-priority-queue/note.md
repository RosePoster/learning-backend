# 堆 / 优先队列核心总结

## 题型分类

### 一、双堆（状态分离）
- 适用：资源存在两种互斥状态，需要分别维护并按不同优先级取用
- 核心思路：将资源按状态分入两个堆，状态转换时在堆之间迁移
- 代表题：`2402`（空闲房间堆 + 会议中房间堆）

### 二、对顶堆（动态中位数）
- 适用：动态维护数据流的中位数
- 核心思路：左最大堆存较小半、右最小堆存较大半，保持堆高平衡
- 配合延迟删除处理滑动窗口场景
- 代表题：`480`

### 三、堆 + 调度 / 冷却
- 适用：任务调度、字符重排等需要冷却间隔的贪心问题
- 核心思路：每轮从堆中取频率最高的元素使用，用完后暂存（冻结），冷却期满再放回堆
- 冻结队列严格 FIFO 时可用普通队列代替堆
- 代表题：`621`、`767`

### 四、k 路归并
- 适用：多个有序链表/流合并取 top-k
- 核心思路：每条链的当前头放入堆，取出最小后推入该链的下一个元素
- 代表题：`355`

---

## 代码模板

### 双堆（资源调度）

```java
// freeRooms: 按资源编号排序（空闲资源池）
PriorityQueue<Integer> freeRooms = new PriorityQueue<>();
// busyRooms: 按结束时间排序，相同则按编号排序（占用资源池）
PriorityQueue<long[]> busyRooms = new PriorityQueue<>(
    (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1])
);

for (int[] meeting : meetings) {
    long start = meeting[0];
    // 释放所有已结束的资源
    while (!busyRooms.isEmpty() && busyRooms.peek()[0] <= start) {
        freeRooms.offer((int) busyRooms.poll()[1]);
    }
    if (!freeRooms.isEmpty()) {
        int room = freeRooms.poll();
        busyRooms.offer(new long[]{meeting[1], room});
    } else {
        long[] earliest = busyRooms.poll();
        busyRooms.offer(new long[]{earliest[0] + meeting[1] - meeting[0], earliest[1]});
    }
}
```

### 堆 + 冷却延迟（调度 / 重排）

```java
PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> b[1] - a[1]); // 按频率降序
Queue<int[]> frozen = new LinkedList<>(); // [解冻时间, 元素, 频率]

while (!available.isEmpty() || !frozen.isEmpty()) {
    // 解冻到期的元素
    while (!frozen.isEmpty() && frozen.peek()[0] <= currentTime) {
        int[] item = frozen.poll();
        available.offer(new int[]{item[1], item[2]});
    }
    if (available.isEmpty()) {
        currentTime = frozen.peek()[0]; // 快进到下一个解冻时刻
        continue;
    }
    int[] best = available.poll();
    // 使用 best，频率减 1
    if (best[1] - 1 > 0) {
        frozen.offer(new int[]{currentTime + cooldown, best[0], best[1] - 1});
    }
    currentTime++;
}
```

### 对顶堆 + 延迟删除（滑动窗口中位数）

```java
PriorityQueue<Integer> maxHeap; // 左半部分（较小值）
PriorityQueue<Integer> minHeap; // 右半部分（较大值）
Map<Integer, Integer> delayCount = new HashMap<>();
int leftSize, rightSize; // 真实有效大小（不含待删除元素）

void insert(int num) {
    if (num <= maxHeap.peek()) { maxHeap.offer(num); leftSize++; }
    else { minHeap.offer(num); rightSize++; }
}

void remove(int num) {
    delayCount.merge(num, 1, Integer::sum);
    if (num <= maxHeap.peek()) leftSize--;
    else rightSize--;
}

void balance() {
    // 保持 leftSize == rightSize 或 leftSize == rightSize + 1
    while (leftSize > rightSize + 1) { minHeap.offer(maxHeap.poll()); leftSize--; rightSize++; prune(maxHeap); }
    while (leftSize < rightSize)     { maxHeap.offer(minHeap.poll()); rightSize--; leftSize++; prune(minHeap); }
}

void prune(PriorityQueue<Integer> heap) {
    while (!heap.isEmpty() && delayCount.getOrDefault(heap.peek(), 0) > 0) {
        delayCount.merge(heap.poll(), -1, Integer::sum);
    }
}
```

### k 路归并

```java
PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);
// 每条链的头入堆
for (Node head : heads) {
    if (head != null) heap.offer(head);
}
while (!heap.isEmpty() && count < k) {
    Node curr = heap.poll();
    result.add(curr.val);
    if (curr.next != null) heap.offer(curr.next);
    count++;
}
```

---

## 题型识别

| 题目特征 | 优先考虑 |
|---|---|
| 资源有空闲/占用两种状态 | 双堆（状态分离） |
| 动态维护中位数 / 滑动窗口中位数 | 对顶堆（+ 延迟删除） |
| 任务调度 + 冷却间隔 / 字符重排不相邻 | 堆 + 冷却延迟 |
| 多个有序流合并取 top-k | k 路归并 |

---

## 关键理解

### 1. 单堆语义不清时，考虑双堆
`2402` 中单堆虽然能 AC，但空闲和占用混在一起，释放逻辑需要反复出入堆。将两种状态拆到各自的堆中，语义更清晰，操作也更自然。

### 2. 冻结队列的 FIFO 性质
`621` 中冻结任务的解冻顺序就是冻结顺序（因为冷却期固定），所以冻结容器可以用普通队列而不需要堆。识别出这一点可以简化实现。

### 3. 延迟删除的核心是维护真实高度
`480` 中 `PriorityQueue` 不支持 O(log n) 删除指定元素，因此用 `delayCount` 记录待删除元素，只在元素出现在堆顶时才真正移除。但必须手动维护左右堆的真实大小，否则 balance 逻辑会出错。

### 4. 堆 + 贪心 = 冷却期为 1 的重排
`767` 本质上是冷却期为 1 的调度问题：每次取频率最高的字符，暂存上一轮字符一轮后再放回。堆为空但字符串未完成时说明无法构造。

### 5. 设计题先确认边界情况
`355` 的主逻辑（关注关系 + 链表 + k 路归并）很直接，但用户可能没有关注任何人、没有发过推文。这类空状态应在设计阶段提前考虑，而非调试时才发现。

---

## 常见坑

1. `endTime` 等累加量可能超出 `int` 范围 → 用 `long`（`2402`）
2. 双堆中，占用堆的比较器需要同时处理结束时间相同的情况 → 次级排序用编号（`2402`）
3. 延迟删除后忘记 prune 堆顶 → balance 移动的可能是待删除元素（`480`）
4. 冷却期边界推演出错（`<` vs `<=`、`+1` vs 不加）→ 在纸上画时间线确认（`621`）
5. 贪心重排时，找到 `isEnd` 后没有处理"堆为空但字符串未完成"的情况 → 需要判断无解（`767`）

---

## 面试话术

**双堆（资源调度）**
- 资源分空闲和占用两种状态。空闲堆按编号排序，占用堆按结束时间排序。每次新任务到来，先把所有已结束的资源释放回空闲堆，再从空闲堆取编号最小的；若无空闲则从占用堆取最早结束的，延长其占用时间。

**对顶堆（中位数）**
- 左最大堆存较小一半，右最小堆存较大一半，保持左堆最多多一个元素。中位数就是左堆顶（奇数）或两堆顶的均值（偶数）。滑动窗口场景配合延迟删除，避免 O(k) 的逐元素删除。

**堆 + 冷却**
- 每轮从可用堆中取频率最高的任务执行，执行后放入冻结队列，冷却期满后放回可用堆。如果可用堆为空但冻结队列不为空，就快进到下一个解冻时刻。
