class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[i] > heights[stack.peek()]) {
                ans[i]++;
                stack.pop();
            }
            if (!stack.isEmpty()) ans[i]++;
            stack.push(i);
        }

        return ans;
    }
}