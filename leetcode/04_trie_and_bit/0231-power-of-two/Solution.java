class Solution {
    public boolean isPowerOfTwo(int n) {
        // A number that is a power of two has exactly one bit set in its binary representation.
        return n > 0 && (n & n - 1) == 0;
    }
}