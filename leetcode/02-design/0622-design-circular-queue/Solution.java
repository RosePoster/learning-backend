class MyCircularQueue {

    private LinkedNode head; // 指向开头元素之前
    private LinkedNode end;  // 指向末尾元素

    public MyCircularQueue(int k) {
        head = new LinkedNode(-1, null);
        end = head;

        LinkedNode curr = head;
        for(int i = 0; i < k; i++) {
            curr.next = new LinkedNode(-1, null);
            curr = curr.next;
        }
        curr.next = head;
    }
    
    public boolean enQueue(int value) {
        if(isFull()) return false;
        end = end.next;
        end.val = value;
        return true;
    }
    
    public boolean deQueue() {
        if(isEmpty()) return false;
        head = head.next;
        return true;
    }
    
    public int Front() {
        if(isEmpty()) return -1;
        return head.next.val;
    }
    
    public int Rear() {
        if(isEmpty()) return -1;
        return end.val;
    }
    
    public boolean isEmpty() {
        return head == end;
    }
    
    public boolean isFull() {
        return end.next == head;
    }
}

class LinkedNode {
    int val;
    LinkedNode next;
    LinkedNode(int val, LinkedNode next) {
        this.val = val;
        this.next = next;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */