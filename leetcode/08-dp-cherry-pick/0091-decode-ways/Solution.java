class Solution {
    public int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;
        int n = s.length();
        int preCount = 1;
        int currCount = 1;

        int prevNum = s.charAt(0) - '0';

        for (int i = 1; i < n; i++) {
            int currNum = s.charAt(i) - '0';
            boolean nextNumIsZero = i + 1 < n && s.charAt(i + 1) == '0';
            int temp = currCount;
            if (currNum == 0) {
                if (nextNumIsZero || prevNum > 2) return 0;
            } else {
                int num = prevNum * 10 + currNum;
                if (!nextNumIsZero && num > 9 && num < 27) {
                    currCount += preCount;
                }
            }
            preCount = temp;
            prevNum = currNum;
        }

        return currCount;
    }
}