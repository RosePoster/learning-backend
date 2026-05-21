class Trie {
    public String word; //节点直接记录word，并用null做非终点判断，省略StringBiluder的消耗
    public Map<Character, Trie> next;

    public Trie () {
        word = null;
        next = new HashMap<>();
    }

    public void add(String s) {
        Trie curr = this;
        for(int i = 0; i < s.length(); i++) {
            curr = curr.next.computeIfAbsent(s.charAt(i), k -> new Trie());
        }
        curr.word = s;
    }
}

class Solution {
    private List<String> ans;
    public List<String> findWords(char[][] board, String[] words) {
        Trie root = buildTrie(words);
        ans = new ArrayList<>();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root);
            }
        }

        return ans;
    }

    public void dfs(char[][] board, int i, int j, Trie curr) {
        // 判断是否合法
        if(
            i < 0 || 
            i >= board.length || 
            j < 0 || 
            j >= board[0].length
        ) return;
        Trie next = curr.next.get(board[i][j]);
        if(next == null) return;
        
        // 访问当前节点
        char c = board[i][j];
        board[i][j] = '#'; // 使用数组本身做标记

        // 收集答案
        if(next.word != null) {
            ans.add(next.word);
            next.word = null;
        }
        
        // 遍历
        dfs(board, i - 1, j, next);
        dfs(board, i + 1, j, next);
        dfs(board, i, j - 1, next);
        dfs(board, i, j + 1, next);
        
        // 回退
        board[i][j] = c;
        
        // 删除非单词终点叶子节点
        if(next.next.isEmpty() && next.word == null) {
            curr.next.remove(c);
        }
    }
    
    public Trie buildTrie(String[] words) {
        Trie trie = new Trie();
        for(String s : words) {
            trie.add(s);
        }
        return trie;
    }
}