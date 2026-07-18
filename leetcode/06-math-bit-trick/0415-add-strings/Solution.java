class Solution {
    public String addStrings(String num1, String num2) {
        int n = num1.length();
        int m = num2.length();
        int[] ans = new int[Math.max(n, m) + 1];
        int idx1 = n - 1;
        int idx2 = m - 1;
        for (int i = ans.length - 1; i > 0; i--) {
            int a = idx1 >= 0 ? num1.charAt(idx1--) - '0' : 0;
            int b = idx2 >= 0 ? num2.charAt(idx2--) - '0' : 0;
            ans[i] += a + b;
            if (ans[i] > 9) {
                ans[i] -= 10;
                ans[i - 1]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = (ans[0] == 0 ? 1 : 0); i < ans.length; i++) {
            sb.append(ans[i]);
        }

        return sb.toString();
    }
}