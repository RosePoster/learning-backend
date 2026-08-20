class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] nextHighers = buildNextHighers(heights);
        int[] ans = new int[n];

        for (int i = 0; i < n - 1; i++) {
            int idx = i + 1;
            while (idx < n) {
                ans[i]++; // 看到一个数
                if (heights[idx] >= heights[i]) break; // 如果他高于等于当前数，停止
                idx = nextHighers[idx]; // 否则，看下一个比当前数高的数
            }
        }

        return ans;
    }

    private int[] buildNextHighers(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int[] nextHighers = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[i] > heights[stack.peek()]) {
                stack.pop();
            }
            nextHighers[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return nextHighers;
    }
}