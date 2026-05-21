class Solution {
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeroCount = 0;
        int ans = 0;
        for(int right = 0; right < nums.length; right++) {
            zeroCount += (nums[right] ^ 1);
            while(zeroCount > k) {
                zeroCount -= (nums[left] ^ 1);
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}