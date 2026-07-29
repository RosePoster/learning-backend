// 解法1
class MyCircularDeque {
    private Node front;
    private Node end;

    public MyCircularDeque(int k) {
        Node currNode = new Node(-1);
        currNode.next = currNode;
        currNode.prev = currNode;
        for (int i = 0; i < k; i++) {
            Node nextNode = new Node(-1);
            nextNode.next = currNode.next;
            nextNode.prev = currNode;
            currNode.next.prev = nextNode;
            currNode.next = nextNode;
            
            currNode = nextNode;
        }
        front = currNode;
        end = currNode;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) return false;
        front.val = value;
        front = front.next;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) return false;
        end = end.prev;
        end.val = value;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) return false;
        front = front.prev;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) return false;
        end = end.next;
        return true;
    }
    
    public int getFront() {
        return isEmpty() ? -1 : front.prev.val;
    }
    
    public int getRear() {
        return isEmpty() ? -1 : end.val;
    }
    
    public boolean isEmpty() {
        return front == end;
    }
    
    public boolean isFull() {
        return front.next == end;
    }

    private static class Node {
        int val;
        Node prev;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }
}

// 解法2
class MyCircularDeque {
    private Node front;
    private Node rear;
    private int size;
    private int maxSize;

    public MyCircularDeque(int k) {
        maxSize = k;
        size = 0;
        front = new Node(-1);
        front.next = front;
        front.prev = front;
        rear = front;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) return false;
        addNodeIfNeed();
        front = front.next;
        front.val = value;
        size++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) return false;
        addNodeIfNeed();
        rear = rear.prev;
        rear.val = value;
        size++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) return false;
        front = front.prev;
        size--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) return false;
        rear = rear.next;
        size--;
        return true;
    }
    
    public int getFront() {
        return isEmpty() ? -1 : front.val;
    }
    
    public int getRear() {
        return isEmpty() ? -1 : rear.val;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean isFull() {
        return size == maxSize;
    }

    private void addNodeIfNeed() {
        if (front.next != rear || size == 0) return;
        Node newNode = new Node(0);
        newNode.next = rear;
        newNode.prev = front;
        rear.prev = newNode;
        front.next = newNode;
    }

    private static class Node {
        int val;
        Node prev;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }
}

// 解法3
class MyCircularDeque {
    private int[] nums;
    private int MOD;
    private int front;
    private int rear;

    public MyCircularDeque(int k) {
        nums = new int[k + 1];
        MOD = k + 1;
        front = 0;
        rear = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) return false;
        nums[front] = value;
        front = (front + 1) % MOD;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) return false;
        rear = (rear + MOD - 1) % MOD;
        nums[rear] = value;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) return false;
        front = (front + MOD - 1) % MOD;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) return false;
        rear = (rear + 1) % MOD;
        return true;
    }
    
    public int getFront() {
        if (isEmpty()) return -1;
        return nums[(front + MOD - 1) % MOD];
    }
    
    public int getRear() {
        if (isEmpty()) return -1;
        return nums[rear];
    }
    
    public boolean isEmpty() {
        return front == rear;
    }
    
    public boolean isFull() {
        return (front + 1) % MOD == rear;
    }
}