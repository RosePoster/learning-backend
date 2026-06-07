class MyHashSet {
    private static final int BUCKETS = 1000;
    private MyLinkedList[] linkHash;

    public MyHashSet() {
        linkHash = new MyLinkedList[BUCKETS];
        for(int i = 0; i < BUCKETS; i++) linkHash[i] = new MyLinkedList(-1, null);
    }
    
    public void add(int key) {
        MyLinkedList node = findPrev(key);
        if(node.getNext() != null) return;
        MyLinkedList curr = new MyLinkedList(key, null);
        node.setNext(curr);
    }
    
    public void remove(int key) {
        MyLinkedList node = findPrev(key);
        MyLinkedList next = node.getNext();
        if(next == null) return;
        node.setNext(next.getNext());
    }
    
    public boolean contains(int key) {
        return findPrev(key).getNext() != null;
    }

    private MyLinkedList findPrev(int key) {
        int index = num2index(key);
        MyLinkedList curr = linkHash[index];
        while(curr.getNext() != null) {
            MyLinkedList next = curr.getNext();
            if(next.getVal() == key) break;
            curr = next;
        }
        return curr;
    }

    private int num2index(int i) {
        return i % BUCKETS;
    }
}

class MyLinkedList {
    private int val;
    private MyLinkedList next;

    MyLinkedList(int val, MyLinkedList next) {
        this.val = val;
        this.next = next;
    }

    public void setVal(int val) {this.val = val;}
    public void setNext(MyLinkedList next) {this.next = next;}
    public int getVal() {return val;}
    public MyLinkedList getNext() {return next;}
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */