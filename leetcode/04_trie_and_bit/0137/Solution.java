class Solution {
    public int singleNumber(int[] nums) {
        int ones = 0;
        int twos = 0;
        for(int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }
        
        // After processing all numbers, 'ones' will contain the unique number that appears only once.
        return ones;
    }
}