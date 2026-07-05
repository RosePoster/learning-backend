class Solution {
    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        int[] leftLowerest = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            leftLowerest[i] = stack.isEmpty() ? i : leftLowerest[stack.peek()];
            stack.push(i);
        }

        stack.clear();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            if (!stack.isEmpty() && nums[leftLowerest[stack.peek()]] < nums[i]) return true;
            stack.push(i);
        }

        return false;
    }
}