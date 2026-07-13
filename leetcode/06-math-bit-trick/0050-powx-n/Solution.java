class Solution {
    public double myPow(double x, int n) {
        if (x == 0) return (double)0;
        if (n == 0) return (double)1;

        long absN = n;
        boolean isNege = false;
        if (absN < 0) {
            absN = -absN;
            isNege = true;
        }

        double rest = 1;
        while (absN > 1) {
            if (absN % 2 == 1) rest *= x;
            absN /= 2;
            x *= x;
        }

        return isNege ? 1 / (rest * x) : rest * x;
    }
}