class LRUCache {

    private Node head;
    private Node tail;
    private Map<Integer, Node> map;
    private int capacity;
    private int len;

    public LRUCache(int capacity) {
        head = new Node(-1, -1, null, null);
        tail = new Node(-1, -1, head, null);
        head.next = tail;
        this.capacity = capacity;
        map = new HashMap<>();
        len = 0;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node == null) return -1;
        removeNode(node);
        putToHead(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        Node node = map.get(key);
        if(node != null) {
            node.val = value;
            removeNode(node);
        }else {
            node = new Node(key, value, null, null);
            map.put(key, node);
            len++;
        }
        putToHead(node);

        if(len > capacity) {
            map.remove(tail.prev.key);
            removeNode(tail.prev);
            len--;
        }
    }

    private void removeNode(Node node) {
        Node preNode = node.prev;
        preNode.next = node.next;
        node.next.prev = preNode;
        node.prev = null;
        node.next = null;
    }

    private void putToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

}


class Node {
    public int key;
    public int val;
    public Node prev;
    public Node next;

    Node(int key, int val, Node prev, Node next) {
        this.key = key;
        this.val = val;
        this.prev = prev;
        this.next = next;
    }
}
/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */