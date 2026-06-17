class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head == null || head.next == null) return head;
        ListNode dummy = new ListNode(-201, head);
        ListNode lessThanX = dummy;
        while(lessThanX.next != null) {
            if(lessThanX.next.val >= x) break;
            lessThanX = lessThanX.next;
        }
        ListNode greaterOrEqualPrev = lessThanX;
        while(greaterOrEqualPrev.next != null) {
            ListNode next = greaterOrEqualPrev.next;
            if(next.val < x) {
                greaterOrEqualPrev.next = next.next;
                next.next = lessThanX.next;
                lessThanX.next = next;
                lessThanX = next;
            }else greaterOrEqualPrev = next;
        }
        return dummy.next;
    }
}