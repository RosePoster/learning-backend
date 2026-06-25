class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int currSum = 0;
        int ans = 0;

        for(int i = 0; i < nums.length; i++) {
            map.merge(currSum, 1, Integer::sum);
            currSum += nums[i];
            ans += map.getOrDefault(currSum - k, 0);
        }

        return ans;
    }
}