class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        boolean isNeg = false;

        StringBuilder sb = new StringBuilder();
        long longN = numerator;
        long longD = denominator;
        if (longN < 0) {
            longN = -longN;
            isNeg = !isNeg;
        }
        if (longD < 0) {
            longD = -longD;
            isNeg = !isNeg;
        }
        if (isNeg) sb.append('-');

        int i = 1;
        while (longN >= longD * i * 10) i *= 10;
        while (i >= 1) {
            int curr = myCount(longN, longD * i);
            longN -= (longD * i * curr);
            sb.append(curr);
            i /= 10;
        }
        
        if (longN != 0) processDecimal(sb, longN, longD);
        return sb.toString();

    }

    private void processDecimal(StringBuilder sb, long longN, long longD) {
        Map<Long, Integer> map = new HashMap<>();
        if (sb.length() == 0 || sb.charAt(sb.length() - 1) == '-') sb.append(0);
        sb.append('.');
        map.put(longN, sb.length());
        while (longN != 0) {
            longN *= 10;
            int curr = myCount(longN, longD);
            sb.append(curr);
            longN -= longD * curr;
            if (map.containsKey(longN)) {
                sb.insert(map.get(longN).intValue(), '(');
                sb.append(')');
                break;
            }
            map.put(longN, sb.length());
        }
    }

    private int myCount(long n, long m) {
        int curr = 0;
        while (n >= m) {
            n -= m;
            curr++;
        }
        return curr;
    }
}