class Solution {
    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        long[] pre = new long[n + 1];
        for (int i = 1; i <= n; i++) pre[i] = pre[i - 1] + nums[i - 1];

        long ans = 0;
        for (int i = 0; i < n; i++) {
            long sum = pre[right[i]] - pre[left[i] + 1];
            ans = Math.max(ans, sum * nums[i]);
        }

        return (int)(ans % 1000000007);
    }
}