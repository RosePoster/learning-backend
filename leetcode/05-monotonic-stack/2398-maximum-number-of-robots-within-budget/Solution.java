class Solution {
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int n = chargeTimes.length;
        int begin = 0;
        int ans = 0;
        long runSum = 0;
        int maxChargeTimeIdx = 0;
        int[] nextGreater = buildNextGreater(chargeTimes);
        for (int end = 0; end < n; end++) {
            runSum += runningCosts[end];
            maxChargeTimeIdx = chargeTimes[end] >= chargeTimes[maxChargeTimeIdx] ? end : maxChargeTimeIdx;
            while ((long)(maxChargeTimeIdx > end ? 0 : chargeTimes[maxChargeTimeIdx]) + (end - begin + 1) * runSum > budget) {
                runSum -= runningCosts[begin];
                if (begin == maxChargeTimeIdx) {
                    maxChargeTimeIdx++;
                    if (maxChargeTimeIdx < end) {
                        while (nextGreater[maxChargeTimeIdx] <= end) {
                            maxChargeTimeIdx = nextGreater[maxChargeTimeIdx];
                        }
                    }
                }
                begin++;
            }
            ans = Math.max(ans, end - begin + 1);
        }

        return ans;
    }

    private int[] buildNextGreater(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int[] nextGreater = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) stack.pop();
            nextGreater[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        return nextGreater;
    }
}
