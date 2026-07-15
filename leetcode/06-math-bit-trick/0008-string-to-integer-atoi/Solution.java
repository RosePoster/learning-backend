class Solution {
    public int myAtoi(String s) {
        int i = 0;
        int n = s.length();

        // 跳过空格
        while (i < n && s.charAt(i) == ' ') i++;

        // 处理符号
        boolean isNego = false;
        if (i < n && (s.charAt(i) == '-' || s.charAt(i) == '+')) {
            if (s.charAt(i) == '-') isNego = true;
            i++;
        }

        // 处理数字
        long ans = 0;
        while (i < n) {
            int num = s.charAt(i++) - '0';
            if (num > 9 || num < 0) break;
            ans *= 10;
            ans += num;
            if (ans > Integer.MAX_VALUE) break;
        }

        // 整理答案
        if (isNego) ans = -ans;
        ans = Math.min(ans, Integer.MAX_VALUE);
        ans = Math.max(ans, Integer.MIN_VALUE);
        return (int)ans;
    }
}