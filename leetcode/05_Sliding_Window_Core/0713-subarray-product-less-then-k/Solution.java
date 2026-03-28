class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k == 0 || k == 1) return 0; // k 为 0 或 1 均无合法子数组
        int left = 0;
        int currProduct = 1; // nums[i] > 0,所以可以初始化为1
        int ans = 0;
        
        for(int right = 0; right < nums.length; right++) {
            // 拓展
            currProduct *= nums[right];
            // 收缩
            while(currProduct >= k) {
                currProduct /= nums[left];
                left++;
            }
            // 收集答案
            ans += right - left + 1;
        }

        return ans;
    }
}