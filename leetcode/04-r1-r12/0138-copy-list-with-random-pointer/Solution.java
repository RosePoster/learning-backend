class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node curr = head;
        Node copy = null;
        while (curr != null) {
            copy = new Node(curr.val);
            copy.random = curr.random;
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }

        curr = head;
        while (curr != null) {
            copy = curr.next;
            copy.random = copy.random == null ? null : copy.random.next;
            curr = curr.next.next;
        }

        Node copyHead = head.next;
        curr = head;
        while (curr != null) {
            copy = curr.next;
            curr.next = copy.next;
            copy.next = copy.next == null ? null : copy.next.next;
            curr = curr.next;
        }

        return copyHead;
    }
}