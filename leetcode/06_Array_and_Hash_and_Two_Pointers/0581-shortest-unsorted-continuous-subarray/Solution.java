class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int len = nums.length;
        int currMax = nums[0];
        int right = len;
        for(int i = 0; i < len; i++) {
            int num = nums[i];
            if(num >= currMax) currMax = num;
            else right = i;
        }        
        if(right == len) return 0;

        int currMin = nums[len - 1];
        int left = len;
        for(int i = len - 1; i >= 0; i--) {
            int num = nums[i];
            if(num <= currMin) currMin = num;
            else left = i;
        }

        return right - left + 1;
    }
}