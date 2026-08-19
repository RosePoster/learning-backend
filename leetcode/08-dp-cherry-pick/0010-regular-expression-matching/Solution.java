class Solution {
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        // 默认'*'不独立出现
        boolean[] dp = new boolean[m];  
        
        // 初始化,可以多用的前缀均可匹配空串
        for (int j = 1; j < m; j += 2) { 
            if (p.charAt(j) == '*') {
                dp[j - 1] = true;
                dp[j] = true;
            } else break;
        }

        for (int i = 0; i < n; i++) {
            char sChar = s.charAt(i);
            boolean pre = i == 0;
            for (int j = 0; j < m; j++) {
                char pChar = p.charAt(j);

                //跳过不独立的'*'
                if (pChar == '*') { 
                    dp[j] = dp[j - 1];
                    continue;
                }
                boolean up = dp[j];
                boolean match = isMatch(sChar, pChar);

                dp[j] = j + 1 < m && p.charAt(j + 1) == '*' ? // 如果可以多用
                        up && match || j > 0 && dp[j - 1] :   // 多1次 或 不用
                        pre && match;                         // 正常匹配
                
                pre = up;
            }
        }

        return dp[m - 1];
    }

    private boolean isMatch(char a, char b) {
        return b == '.' || b == a;        
    }
}