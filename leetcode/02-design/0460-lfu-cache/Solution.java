class LFUCache {

    private Map<Integer, Node> valMap;
    private Map<Integer, DoublyLinkedList> freqMap;
    private int minFreq;
    private int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        valMap = new HashMap<>();
        freqMap = new HashMap<>();
        minFreq = 0;
    }
    
    public int get(int key) {
        Node curr = valMap.get(key);
        if(curr == null) return -1;
        removeNode(curr);
        updateAndInsertNode(curr);
        return curr.val;
    }
    
    public void put(int key, int value) {
        Node curr = valMap.get(key);
        if(curr != null) {
            curr.val = value;
            removeNode(curr);
        }
        else {
            if(valMap.size() == capacity) removeLast();
            curr = new Node(key, value, 0);
            valMap.put(key, curr);
        }
        updateAndInsertNode(curr);
    }

    private void removeLast() {
        Node curr = freqMap.get(minFreq).head.prev;
        removeNode(curr);
        valMap.remove(curr.key);
    }

    private void removeNode(Node curr) {
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;

        int freq = curr.freq;
        DoublyLinkedList bucket = freqMap.get(freq);
        if(bucket.head.next == bucket.head) {
            freqMap.remove(curr.freq);
            if(minFreq == freq) minFreq++;
        }
    }

    private void updateAndInsertNode(Node curr) {
        curr.freq++;
        int freq = curr.freq;
        DoublyLinkedList bucket = freqMap.computeIfAbsent(freq, k -> new DoublyLinkedList(k));
        Node head = bucket.head;
        curr.next = head.next;
        curr.prev = head;
        curr.next.prev = curr;
        curr.prev.next = curr;
        if(freq == 1) minFreq = 1;
    }
}

class DoublyLinkedList {
    Node head;
    int freq;

    DoublyLinkedList(int freq) {
        head = new Node(-1, -1, freq);
        this.freq = freq;
    }
} 

class Node {
    int key;
    int val;
    int freq;
    Node prev;
    Node next;

    Node(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
        this.prev = this;
        this.next = this;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */