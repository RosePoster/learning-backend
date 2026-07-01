public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode fast = head;
        ListNode slow = head;

        do {
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != null && fast.next != null && fast != slow);

        if (fast == null || fast.next == null) return null;

        ListNode fromHead = head;
        ListNode fromCont = fast;

        while(fromHead != fromCont) {
            fromHead = fromHead.next;
            fromCont = fromCont.next;
        }

        return fromHead;
    }
}