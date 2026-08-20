# 专题 · 堆 / 优先队列

> 类型：专题总结 ｜ 来源：旧 Round 3

## 摘要

四类模式：双堆（状态分离）、对顶堆（动态中位数 + 延迟删除）、堆 + 冷却调度、k 路归并。共同点是用堆维护"下一个该取谁"，差异在状态的组织方式。

## 题型分类

| 类型 | 适用 | 核心 | 代表题 |
|---|---|---|---|
| 双堆（状态分离） | 资源有空闲/占用两种互斥状态 | 按状态分入两堆，状态转换时迁移 | 2402 |
| 对顶堆 | 动态中位数 | 左最大堆存较小半、右最小堆存较大半，保持高度平衡；滑窗场景配延迟删除 | 0480 |
| 堆 + 冷却 | 调度 / 重排含冷却间隔 | 每轮取频率最高元素，用完冻结，期满放回；冷却期固定时冻结容器用普通队列即可 | 0621 / 0767 |
| k 路归并 | 多有序流合并取 top-k | 各链头入堆，取出后推入该链下一个 | 0355 |

## 模板

双堆（资源调度）：

```java
PriorityQueue<Integer> freeRooms = new PriorityQueue<>(); // 按编号
PriorityQueue<long[]> busyRooms = new PriorityQueue<>(    // 按结束时间，次级按编号
    (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

for (int[] meeting : meetings) {
    long start = meeting[0];
    while (!busyRooms.isEmpty() && busyRooms.peek()[0] <= start)
        freeRooms.offer((int) busyRooms.poll()[1]);          // 释放已结束资源
    if (!freeRooms.isEmpty()) {
        busyRooms.offer(new long[]{meeting[1], freeRooms.poll()});
    } else {
        long[] earliest = busyRooms.poll();
        busyRooms.offer(new long[]{earliest[0] + meeting[1] - meeting[0], earliest[1]});
    }
}
```

堆 + 冷却延迟：

```java
PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> b[1] - a[1]); // 频率降序
Queue<int[]> frozen = new LinkedList<>(); // [解冻时间, 元素, 频率]

while (!available.isEmpty() || !frozen.isEmpty()) {
    while (!frozen.isEmpty() && frozen.peek()[0] <= currentTime) {
        int[] item = frozen.poll();
        available.offer(new int[]{item[1], item[2]});
    }
    if (available.isEmpty()) { currentTime = frozen.peek()[0]; continue; } // 快进
    int[] best = available.poll();
    if (best[1] - 1 > 0) frozen.offer(new int[]{currentTime + cooldown, best[0], best[1] - 1});
    currentTime++;
}
```

对顶堆 + 延迟删除（滑动窗口中位数）：

```java
PriorityQueue<Integer> maxHeap; // 较小半
PriorityQueue<Integer> minHeap; // 较大半
Map<Integer, Integer> delayCount = new HashMap<>();
int leftSize, rightSize; // 真实有效大小（不含待删元素）

void insert(int num) {
    if (num <= maxHeap.peek()) { maxHeap.offer(num); leftSize++; }
    else { minHeap.offer(num); rightSize++; }
}
void remove(int num) {
    delayCount.merge(num, 1, Integer::sum);
    if (num <= maxHeap.peek()) leftSize--; else rightSize--;
}
void balance() { // 保持 leftSize == rightSize 或 +1
    while (leftSize > rightSize + 1) { minHeap.offer(maxHeap.poll()); leftSize--; rightSize++; prune(maxHeap); }
    while (leftSize < rightSize)     { maxHeap.offer(minHeap.poll()); rightSize--; leftSize++; prune(minHeap); }
}
void prune(PriorityQueue<Integer> heap) {
    while (!heap.isEmpty() && delayCount.getOrDefault(heap.peek(), 0) > 0)
        delayCount.merge(heap.poll(), -1, Integer::sum);
}
```

k 路归并：各链头入堆，`while (!heap.isEmpty() && count < k)` 取最小并推入其后继。

## 关键理解

1. 单堆语义不清时考虑双堆：2402 单堆可 AC，但空闲/占用混在一起导致释放逻辑反复出入堆；状态拆分后操作自然。
2. 冷却期固定时冻结顺序即 FIFO，冻结容器用普通队列而非堆（0621）。
3. 延迟删除的核心是维护真实高度：`PriorityQueue` 不支持定点删除，用 `delayCount` 记账、堆顶出现时才真删，但左右堆真实大小必须手动维护，否则 balance 出错（0480）。
4. 0767 本质是冷却期为 1 的调度；堆空而字符串未完成 → 无解。
5. 设计题先确认空状态边界（0355：用户无关注、无推文），设计阶段处理而非调试时发现。

## 题型识别

| 特征 | 优先考虑 |
|---|---|
| 资源有空闲/占用状态 | 双堆 |
| 动态 / 滑窗中位数 | 对顶堆 + 延迟删除 |
| 调度含冷却 / 重排不相邻 | 堆 + 冷却 |
| 多有序流合并 top-k | k 路归并 |

## 常见坑

1. `endTime` 类累加量溢出 int → long（2402）。
2. 占用堆比较器需处理结束时间相同 → 次级按编号（2402）。
3. 延迟删除后忘 prune 堆顶 → balance 可能移动待删元素（0480）。
4. 冷却边界（`<` vs `<=`、`+1`）→ 纸上画时间线确认（0621）。
5. 贪心重排未处理"堆空但未完成" → 需判无解（0767）。

## 面试表述

双堆：空闲堆按编号、占用堆按结束时间；新任务先释放已结束资源，有空闲取编号最小，否则取最早结束者延长占用。对顶堆：左最大堆存较小半、右最小堆存较大半，左堆最多多一个；中位数即左堆顶或两堆顶均值；滑窗配延迟删除避免 O(k) 逐个删。堆 + 冷却：每轮取频率最高执行后冻结，期满放回；可用堆空则快进到下一解冻时刻。
