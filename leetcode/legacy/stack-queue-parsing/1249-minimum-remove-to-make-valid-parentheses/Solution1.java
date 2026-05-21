class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder();
        int leftCount = 0;
        int left = 0;
        int right = 0;
        int len = s.length();
        while(right < len) {
            for(; right < len; right++) {
                if(s.charAt(right) == '(') leftCount++;
                if(s.charAt(right) == ')') {
                    if (leftCount == 0) break;
                    leftCount--;
                }
            }
            if(right < len) {
                for(; left < right; left++) sb.append(s.charAt(left));
                left++;
                right++;
            }
        }
        StringBuilder rest = new StringBuilder();
        for(int i = len - 1; i >= left; i--) {
            char c = s.charAt(i);
            if(c == '(' && leftCount > 0) {
                leftCount--;
                continue;
            }
            rest.append(c);
        }
        return sb.append(rest.reverse()).toString();
    }
}