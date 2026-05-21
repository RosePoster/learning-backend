class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int curr = 0;
        for(int i = 0; i < k; i++) {
            curr += nums[i];
        }
        int ans = curr;

        for(int i = k; i < nums.length; i++) {
            curr += nums[i];
            curr -= nums[i - k];
            ans = Math.max(ans, curr);
        }

        return (double)ans / k;
    }
}