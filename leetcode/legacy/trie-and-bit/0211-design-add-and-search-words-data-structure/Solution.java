class Trie {
    boolean isEnd;
    // 这里用数组效率比用HashMap更高，因为匹配通用字符时需要遍历所有子节点
    public Trie[] next;
    
    Trie() {
        isEnd = false;
        next = new Trie[26];
    }
}

class WordDictionary {

    Trie trie;

    public WordDictionary() {
        trie = new Trie();
    }
    
    public void addWord(String word) {
        Trie curr = trie;
        for(int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';

            // 如果当前字符对应的子节点不存在，则创建一个新的子节点
            if(curr.next[c] == null) {
                curr.next[c] = new Trie();
            }
            curr = curr.next[c];
        }
        curr.isEnd = true;
    }
    
    public boolean search(String word) {
        return isMatch(word, 0, trie);
    }

    private boolean isMatch(String word, int begin, Trie node) {
        // 匹配完成
        if(begin == word.length()) {
            return node.isEnd;
        }

        char c = word.charAt(begin);

        // 匹配任意字符
        if(c == '.') {
            for(Trie nextNode : node.next) {
                if(nextNode != null && isMatch(word, begin + 1, nextNode)) {
                    return true;
                }
            }
            return false;
        }

        // 匹配普通字符
        return node.next[c - 'a'] != null && isMatch(word, begin + 1, node.next[c - 'a']);
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */