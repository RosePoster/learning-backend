/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode begin = new ListNode();
        begin.next = head;
        head = begin;
        ListNode end = begin.next;
        while(end != null) {
            for(int i = 0; i < k; i++) {
                if(end == null) return head.next;
                end = end.next;
            }
            ListNode temp = begin.next;
            begin.next = reverse(temp, end);
            begin = temp;
        }
        return head.next;
    }

    public ListNode reverse(ListNode begin, ListNode end) {
        ListNode curr = begin.next;
        begin.next = end;
        while(curr != end) {
            ListNode temp = curr.next;
            curr.next = begin;
            begin = curr;
            curr = temp;
        }
        return begin;
    }
}