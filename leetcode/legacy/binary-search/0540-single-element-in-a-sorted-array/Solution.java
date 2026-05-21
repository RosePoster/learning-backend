class Solution {
    public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1) return nums[0];
        int begin = 0;
        int end = nums.length - 1;
        while(begin < end) {
            int mid = begin + (end - begin) / 2;
            // 判断mid是否是答案
            if(nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }
            // 禁止nums[mid] == nums[mid] + 1的情况
            if(nums[mid] != nums[mid - 1]) mid--;
            int leftLength = mid - begin + 1;
            if((leftLength & 1) == 1) {
                end = mid;
            }else {
                begin = mid + 1;
            }
        }
        return nums[begin];
    }
}