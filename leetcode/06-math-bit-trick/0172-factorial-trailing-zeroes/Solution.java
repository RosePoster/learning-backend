class Solution {
    public int trailingZeroes(int n) {
        int fiveCount = 0;
        while (n > 0) {
            fiveCount += n / 5;
            n /= 5;
        }
        return fiveCount;
    }
}