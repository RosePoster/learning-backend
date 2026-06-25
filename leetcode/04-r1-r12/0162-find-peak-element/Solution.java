class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while(left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid - 1]) {
                if (nums[mid] > nums[mid + 1]) return mid;
                left = mid + 1;
            } else right = mid - 1;
        }

        return nums[left] > nums[right] ? left : right;
    }
}