class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        return mostKDistinct(nums, k) - mostKDistinct(nums, k - 1);
    }

    private int mostKDistinct(int[] nums, int k) {
        int ans = 0;
        int left = 0;
        int[] counts = new int[nums.length + 1];
        int diffNums = 0;

        for(int right = 0; right < nums.length; right++) {
            int newNum = nums[right];
            if(counts[newNum] == 0) diffNums++;
            counts[newNum]++;

            while(diffNums > k) {
                int oldNum = nums[left];
                counts[oldNum]--;
                left++;
                if(counts[oldNum] == 0) diffNums--;
            }

            ans += right - left + 1;
        }

        return ans;
    }
}