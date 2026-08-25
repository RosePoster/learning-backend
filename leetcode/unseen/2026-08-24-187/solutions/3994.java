class Solution {
    private final static int MOD = 1000000007;
    public int minAdjacentSwaps(int[] nums, int a, int b) {
        int ans = 0;
        int p1end = -1;
        int p2end = -1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num < a) {
                ans = (ans + i - p1end - 1) % MOD;
                p1end++;
                p2end++;
            } else if (num <= b) {
                ans = (ans + i - p2end - 1) % MOD;
                p2end++;
            }
        }
        return ans;
    }
}