class Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        long totalMax = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            int right = stack.isEmpty() ? n : stack.peek();
            int rightLen = right - i;
            int leftLen = i - left[i];
            totalMax += (long) leftLen * rightLen * nums[i];
            stack.push(i);
        }

        stack.clear();
        long totalMin = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
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
            int right = stack.isEmpty() ? n : stack.peek();
            int rightLen = right - i;
            int leftLen = i - left[i];
            totalMin += (long) leftLen * rightLen * nums[i];
            stack.push(i);
        }

        return totalMax - totalMin;
    }
}