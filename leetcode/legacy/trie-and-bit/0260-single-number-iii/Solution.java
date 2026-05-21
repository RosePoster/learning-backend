class Solution {
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        // XOR all the numbers to get the XOR of the two unique numbers
        for(int num : nums) {
            xor ^= num;
        }
        // Get the rightmost set bit
        int divider = xor & -xor;
        int[] ans = new int[2];
        for(int num : nums) {
            // Divide the numbers into two groups based on the rightmost set bit
            ans[0] ^= (num & divider) == 0 ? num : 0;
            ans[1] ^= (num & divider) != 0 ? num : 0;             
        }
        return ans;
    }
}