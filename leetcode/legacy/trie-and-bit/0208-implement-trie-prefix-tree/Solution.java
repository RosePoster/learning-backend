class TreeNode {
    // 辅助类，表示 Trie 树的节点
    
    // isEnd 表示以该节点为路径的字符串是否存在于 Trie 树中
    public boolean isEnd;
    // childNode 表示以该节点为路径的字符串的下一个字符和对应的子节点
    // 也可以用数组来表示 childNode，数组的大小为 26，childNode[i] 表示以该节点为路径的字符串的下一个字符为 'a' + i 的子节点
    private Map<Character, TreeNode> childNode;
    
    public TreeNode() {
        isEnd = false;
        this.childNode = new HashMap<>();
    }
    
    public void add(char c, TreeNode child) {
        childNode.put(c, child);
    }

    public TreeNode get(char c) {
        return childNode.get(c);
    }
}

class Trie {

    private TreeNode trieTree;

    public Trie() {
        trieTree = new TreeNode();
    }
    
    public void insert(String word) {
        TreeNode curr = trieTree;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TreeNode next = curr.get(c);
            if(next == null) {
                next = new TreeNode();
                curr.add(c, next);                
            }
            curr = next;
        }
        curr.isEnd = true;
    }
    
    public boolean search(String word) {
        TreeNode node = findNode(word);
        return node != null && node.isEnd == true;
    }
    
    public boolean startsWith(String prefix) {
        TreeNode node = findNode(prefix);
        return node != null;
    }

    // 查找 Trie 树中是否存在以 s 为路径的节点，如果存在，返回该节点；否则返回 null
    private TreeNode findNode(String s) {
        TreeNode curr = trieTree;
        for(int i = 0; i < s.length(); i++) {
            curr = curr.get(s.charAt(i));
            if(curr == null) {
                break;           
            }
        }
        return curr;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */