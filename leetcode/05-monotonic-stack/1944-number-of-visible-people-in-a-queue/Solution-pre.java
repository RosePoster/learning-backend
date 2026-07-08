class Solution {
    public int[] canSeePersonsCount(int[] heights) {
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

        int[] ans = new int[n];
        for (int i = 0; i < n - 1; i++) {
            int idx = i + 1;
            while (idx < n && heights[idx] < heights[i]) {
                idx = nextHighers[idx];
                ans[i]++;
            }
            if (idx < n) ans[i]++;
        }

        return ans;
    }
}