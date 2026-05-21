class Trie {
    public Trie[] next;
    public boolean isEnd;
    Trie() {
        next = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie curr = this;
        for(int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if(curr.next[c] == null) curr.next[c] = new Trie();
            curr = curr.next[c];
        }
        curr.isEnd = true;
    }
}
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Trie root = new Trie();
        int len = s.length();
        for(String word : wordDict) root.insert(word);
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;

        for(int i = 0; i < len; i++) {
            if(dp[i] == false) continue;
            Trie curr = root;
            for(int j = i; j < len; j++) {
                int c = s.charAt(j) - 'a';
                curr = curr.next[c];
                if(curr == null) break;
                if(curr.isEnd) dp[j + 1] = true;
            }
        }
        return dp[len];
    }
}
