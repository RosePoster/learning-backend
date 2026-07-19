class LFUCache {
    private int capacity;
    private Map<Integer, Node> nodeMap;
    private Map<Integer, Node> freqMap;
    private int minFreq;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        minFreq = 1;
    }
    
    public int get(int key) {
        Node node = nodeMap.get(key);
        if (node == null) return -1;
        deleteNode(node);
        node.freq++;
        addNode(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        Node node = nodeMap.get(key);
        if (node != null) {
            node.val = value;
            get(key);
            return;
        }
        if (nodeMap.size() == capacity) deleteNode(freqMap.get(minFreq).prev);
        node = new Node(key, value, 1);
        addNode(node);
    }

    private void deleteNode(Node node) {
        Node prev = node.prev;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        if (prev.next == prev) {
            if (prev.freq == minFreq) minFreq++;
            freqMap.remove(prev.freq);
        }
        nodeMap.remove(node.key);
    }

    private void addNode(Node node) {
        int freq = node.freq;
        nodeMap.put(node.key, node);
        Node freqNode = freqMap.computeIfAbsent(
            freq,
            k -> new Node(-1, -1, k)
        );
        node.next = freqNode.next;
        node.prev = freqNode;
        freqNode.next.prev = node;
        freqNode.next = node;
        minFreq = Math.min(minFreq, freq);
    }
}

class Node {
    int key;
    int val;
    int freq;
    Node prev;
    Node next; 

    public Node(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
        this.prev = this;
        this.next = this;
    }
}