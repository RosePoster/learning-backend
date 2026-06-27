class Solution {
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[slow];
        while(fast != slow) {
            fast = nums[fast];
            fast = nums[fast];
            slow = nums[slow];
        }

        int fromHead = 0;
        int fromSlow = slow;
        while(fromHead != fromSlow) {
            fromHead = nums[fromHead];
            fromSlow = nums[fromSlow];
        }

        return fromHead;
    }
}