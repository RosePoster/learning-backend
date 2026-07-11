class Solution {
    public int[] mostCompetitive(int[] nums, int k) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && n - i > k - stack.size() && nums[i] < stack.peek()) {
                stack.pop();
            }
            if (stack.size() < k) stack.push(nums[i]);
        }

        int[] ans = new int[k];
        for (int i = k - 1; i >= 0; i--) ans[i] = stack.pop();
        return ans;
    }
}