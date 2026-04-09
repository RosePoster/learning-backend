class Solution {
    public int minDistance(String word1, String word2) {
        int[] dp = new int[word1.length() + 1];        
        for(int i = 0; i <= word1.length(); i++) dp[i] = i;

        for(int j = 0; j < word2.length(); j++) {
            int preDiag = dp[0];
            dp[0]++;
            for(int i = 0; i < word1.length(); i++) {
                int pre = dp[i + 1];
                int curr = Math.min(dp[i + 1], dp[i]) + 1;
                if(word1.charAt(i) != word2.charAt(j))
                    preDiag++;
                curr = Math.min(preDiag, curr);
                dp[i + 1] = curr;
                preDiag = pre;
            }
        }
        return dp[word1.length()];
    }
}