class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        int ans = target - (nums[0] + nums[1] + nums[2]);
        if(len == 3) return target - ans;

        for(int i = 0; i < len - 2; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) continue;
            if(nums[i] + nums[len - 1] + nums[len - 2] < target) {
                int curr =  target - (nums[i] + nums[len - 1] + nums[len - 2]);
                ans = Math.abs(curr) > Math.abs(ans) ? ans : curr;
                continue;
            }
            if(nums[i] + nums[i + 1] + nums[i + 2] > target) {
                int curr =  target - (nums[i] + nums[i + 1] + nums[i + 2]);
                ans = Math.abs(curr) > Math.abs(ans) ? ans : curr;
                break;
            }
            int begin = i + 1;
            int end = len - 1;
            while(begin < end) {
                int curr =  target - (nums[i] + nums[begin] + nums[end]);
                ans = Math.abs(curr) > Math.abs(ans) ? ans : curr;
                if(curr == 0) return target;
                else if(curr < 0) end--;
                else begin++;
            }
        }

        return target - ans;
    }
}