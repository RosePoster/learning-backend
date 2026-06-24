class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            int leftNum = nums[left];
            int rightNum = nums[right];
            int midNum = nums[mid];

            if (target == midNum) return mid;
            if (midNum >= leftNum) {
                if (target < leftNum || target > midNum) left = mid + 1;
                else right = mid - 1;
            } else {
                if (target > rightNum || target < midNum) right = mid - 1;
                else left = mid + 1;
            }
        }

        return -1;
    }
}