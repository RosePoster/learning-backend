class Solution {
    public int divide(int dividend, int divisor) {
        boolean isNege = false;

        long absDividend = dividend;
        long absDivisor = divisor;
        if (absDividend < 0) {
            absDividend = -absDividend;
            isNege = !isNege;
        }
        if (absDivisor < 0) {
            absDivisor = -absDivisor;
            isNege = !isNege;
        }

        long n = 1;
        long result = 0;
        while (absDividend >= absDivisor || n > 1) {
            if (absDividend >= absDivisor) {
                if (absDividend > (absDivisor << 1)) {
                    absDivisor <<= 1;
                    n <<= 1;
                } else {
                    absDividend -= absDivisor;
                    result += n;
                }
            } else {
                absDivisor >>= 1;
                n >>= 1;
            }
        }

        if (isNege) result = -result;
        result = Math.min(Integer.MAX_VALUE, result);
        result = Math.max(Integer.MIN_VALUE, result);
        
        return (int)result;
    }
}