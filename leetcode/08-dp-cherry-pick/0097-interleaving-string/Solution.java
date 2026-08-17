class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();

        if (n + m != s3.length()) return false;

        int s1Idx = 0;
        int s2Idx = 0;

        boolean[] dp = new boolean[m + 1];

        while (true) {
            // forced / greedy phase
            while (s1Idx < n || s2Idx < m) {
                char s3c = s3.charAt(s1Idx + s2Idx);

                boolean fromS1 =
                    s1Idx < n && s1.charAt(s1Idx) == s3c;

                boolean fromS2 =
                    s2Idx < m && s2.charAt(s2Idx) == s3c;

                if (!fromS1 && !fromS2) return false;

                // 出现分叉，切换到 DP
                if (fromS1 && fromS2) break;

                if (fromS1) {
                    s1Idx++;
                } else {
                    s2Idx++;
                }
            }

            if (s1Idx == n && s2Idx == m) return true;

            /*
             * 当前唯一状态为 (s1Idx, s2Idx)。
             * 重建这一轮 DP。
             * 这里不需要Array.fill，因为s2Idx后面的内容必然全为false
             */
            dp[s2Idx] = true;

            /*
             * 先补完整当前 s1Idx 这一行。
             *
             * 即从 (s1Idx, s2Idx) 开始只向右走。
             */
            for (int j = s2Idx; j < m && dp[j]; j++)
                dp[j + 1] = s2.charAt(j) == s3.charAt(s1Idx + j);

            /*
             * 正常逐行 rolling DP。
             */
            for (int i = s1Idx; i < n; i++) {
                int trueSize = 0;
                int trueIndex = -1;

                // 当前新行的第 s2Idx 个状态
                dp[s2Idx] =
                    dp[s2Idx]
                    && s1.charAt(i) == s3.charAt(i + s2Idx);

                if (dp[s2Idx]) {
                    trueSize++;
                    trueIndex = s2Idx;
                }

                for (int j = s2Idx; j < m; j++) {
                    char curr = s3.charAt(i + j + 1);

                    boolean fromS1 =
                        dp[j + 1] && curr == s1.charAt(i);

                    boolean fromS2 =
                        dp[j] && curr == s2.charAt(j);

                    dp[j + 1] = fromS1 || fromS2;

                    if (dp[j + 1]) {
                        trueSize++;
                        trueIndex = j + 1;
                    }
                }

                s1Idx = i + 1;

                // 已经算到最后一行
                if (s1Idx == n) {
                    return dp[m];
                }

                // 整行无 reachable state
                if (trueSize == 0) {
                    return false;
                }

                // 整行只剩一个 reachable state，重新退化成 forced phase
                if (trueSize == 1) {
                    s2Idx = trueIndex;
                    break;
                }
            }
        }
    }
}