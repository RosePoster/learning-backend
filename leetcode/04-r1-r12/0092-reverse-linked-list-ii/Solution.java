class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyHead = new ListNode(-1, head); 
        ListNode leftNode = dummyHead;
        for(int i = 0; i < left - 1; i++) {
            leftNode = leftNode.next;
        }

        ListNode reverseBegin = leftNode.next;
        ListNode pre = reverseBegin;
        ListNode curr = pre.next;
        for(int i = 0; i < right - left; i++) {
            ListNode nex = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nex;
        }
        
        leftNode.next = pre;
        reverseBegin.next = curr;
        return dummyHead.next;
    }
}