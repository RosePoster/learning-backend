class Trie {
    // val is the value of single key
    int val;
    Trie[] next;

    public Trie() {
        val = 0;
        next = new Trie[26];
    }

    public void add(String key, int val) {
        Trie curr = this;
        for(int i = 0; i < key.length(); i++) {
            int c = key.charAt(i) - 'a';
            if(curr.next[c] == null) {
                curr.next[c] = new Trie();
            }
            curr = curr.next[c];
        }
        curr.val = val;
    }
}

class MapSum {

    Trie root;

    public MapSum() {
        root = new Trie();
    }
    
    public void insert(String key, int val) {
        root.add(key, val);
    }
    
    public int sum(String prefix) {
        Trie curr = root;
        // find the Trie node that represents the prefix, time complexity is O(m) where m is the length of the prefix
        for(int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if(curr.next[c] == null) {
                return 0;
            }
            curr = curr.next[c];
        }
        // collect the sum of all keys that have the prefix represented by curr, time complexity is O(n) where n is the number of keys that have the prefix represented by curr
        return collect(curr);
    }

    // helper function to collect the sum of all keys that have the prefix represented by curr
    private int collect(Trie curr) {
        int ans = curr.val;
        for(Trie next : curr.next) {
            if(next != null) {
                ans += collect(next);
            }
        }
        return ans;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */