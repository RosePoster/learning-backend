class Solution {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        int n = num1.length();
        int m = num2.length();
        char[] result = new char[m + n];
        Arrays.fill(result, '0');
        for (int i = 0; i < n; i++) {
            int digit = num1.charAt(i) - '0';
            for (int j = 0; j < m; j++) {
                int curr = digit * (num2.charAt(j) - '0');
                myAdd(result, i + j + 1, curr % 10);
                myAdd(result, i + j, curr / 10);
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < m + n && result[i] == '0') i++;
        for (; i < m + n; i++) {
            sb.append(result[i]);
        }

        return sb.toString();
    }

    private void myAdd(char[] result, int idx, int digit) {
        result[idx] += digit;
        while (idx > 0 && result[idx] > '9') {
            result[idx] -= 10;
            result[--idx]++;
        }
    }
}