class MyHashMap {

    private static final int SIZE = 997;
    private Node[] buckets;

    public MyHashMap() {
        buckets = new Node[SIZE];
        for(int i = 0; i < SIZE; i++) buckets[i] = new Node(-1, -1, null);
    }
    
    public void put(int key, int value) {
        Node node = getNode(key);
        if(node.next == null) node.next = new Node(key, value, null);
        node.next.val = value;
    }
    
    public int get(int key) {
        Node node = getNode(key);
        return node.next == null ? -1 : node.next.val;
    }
    
    public void remove(int key) {
        Node node = getNode(key);
        if(node.next != null) node.next = node.next.next;
    }

    private int key2index(int key) {
        return key % SIZE;
    }

    private Node getNode(int key) {
        Node head = buckets[key2index(key)];
        while(head.next != null) {
            if(head.next.key == key) break;
            head = head.next;
        }
        return head;
    }
}

class Node {
    int key;
    int val;
    Node next;

    Node(int key, int val, Node next) {
        this.key = key;
        this.val = val;
        this.next = next;
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */