class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        // prepare
        Map<Integer, Integer> delayCount = new HashMap<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int maxHeapSize = 0;
        int minHeapSize = 0;
        double[] midNums = new double[nums.length - k + 1];

        // build initial window
        for (int i = 0; i < k; i++) {
            maxHeap.offer(nums[i]);
            maxHeapSize++;
        }
        for (int i = 0; i < k / 2; i++) {
            minHeap.offer(maxHeap.poll());
            maxHeapSize--;
            minHeapSize++;
        }

        // collect first window
        midNums[0] = getMedian(maxHeap, minHeap, maxHeapSize, minHeapSize);

        // slide the window
        for (int i = k; i < nums.length; i++) {
            int removeEle = nums[i - k];
            int addEle = nums[i];

            // 标记需要延迟删除的元素
            delayCount.merge(removeEle, 1, Integer::sum);

            // remove
            if (!maxHeap.isEmpty() && removeEle <= maxHeap.peek()) {
                maxHeapSize--;
            } else {
                minHeapSize--;
            }

            // add
            if (!maxHeap.isEmpty() && addEle <= maxHeap.peek()) {
                maxHeap.offer(addEle);
                maxHeapSize++;
            } else {
                minHeap.offer(addEle);
                minHeapSize++;
            }

            // 清理，必须先清理，不然可能会导致平衡错误
            prune(maxHeap, delayCount);
            prune(minHeap, delayCount);

            // balance
            while (maxHeapSize - minHeapSize > 1) {
                minHeap.offer(maxHeap.poll());
                maxHeapSize--;
                minHeapSize++;
                prune(maxHeap, delayCount); // 这里需要清理，因为可能会把需要删除的元素移到另一个堆顶，导致平衡错误
            }
            while (minHeapSize - maxHeapSize > 1) {
                maxHeap.offer(minHeap.poll());
                maxHeapSize++;
                minHeapSize--;
                prune(minHeap, delayCount); // 这里需要清理，因为可能会把需要删除的元素移到另一个堆顶，导致平衡错误
            }
            
            midNums[i - k + 1] = getMedian(maxHeap, minHeap, maxHeapSize, minHeapSize);
        }
        return midNums;
    }

    // 清理堆顶
    private void prune(PriorityQueue<Integer> heap, Map<Integer, Integer> delayCount) {
        while (!heap.isEmpty() && delayCount.getOrDefault(heap.peek(), 0) > 0) {
            delayCount.merge(heap.peek(), -1, Integer::sum);
            heap.poll();
        }
    }

    // 获取中位数
    private double getMedian(
        PriorityQueue<Integer> maxHeap, 
        PriorityQueue<Integer> minHeap, 
        int maxHeapSize, 
        int minHeapSize
    ) {
        if (maxHeapSize > minHeapSize) {
            return maxHeap.peek();
        } else if (minHeapSize > maxHeapSize) {
            return minHeap.peek();
        } else {
            return ((double) maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
}

// 可以规定左堆(maxHeap)始终大于等于右堆(minHeap)，这样可以简化getMedian的判断逻辑
// 但在balance阶段仍然需要双向调整来维持这一约束，因此没有强制应用这一优化