class Trie {
    // val is the sum of all keys that have the prefix represented by this Trie node
    int val;
    Trie[] next;

    public Trie() {
        val = 0;
        next = new Trie[26];
    }

    public void add(String key, int val) {
        Trie curr = this;
        this.val += val;
        for(int i = 0; i < key.length(); i++) {
            int c = key.charAt(i) - 'a';
            if(curr.next[c] == null) {
                curr.next[c] = new Trie();
            }
            curr = curr.next[c];
            curr.val += val;
        }
    }
}

class MapSum {

    private Trie root;
    // need map to store the value of each key, 
    // so that we can calculate the delta when updating the value of an existing key
    private Map<String, Integer> map;

    public MapSum() {
        root = new Trie();
        map = new HashMap<>();
    }
    
    public void insert(String key, int val) {
        // calculate the delta between the new value and the old value (if the key already exists)
        int delta = val - map.getOrDefault(key, 0);
        // update the value of the key in the map
        map.put(key, val);
        root.add(key, delta);
    }
    
    public int sum(String prefix) {
        Trie curr = root;
        for(int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if(curr.next[c] == null) {
                return 0;
            }
            curr = curr.next[c];
        }
        return curr.val;
    }

}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */