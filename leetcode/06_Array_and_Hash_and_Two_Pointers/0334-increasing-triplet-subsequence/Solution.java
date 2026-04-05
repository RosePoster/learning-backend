class Solution {
    public boolean increasingTriplet(int[] nums) {
        int len = nums.length;
        if(len < 3) return false;
        int oneNum = nums[0];
        int twoNum = Integer.MAX_VALUE;
        for(int num : nums) {
            if(num > twoNum) return true;
            else if(num > oneNum) twoNum = num;
            else oneNum = num;
        }
        return false;
    }
}