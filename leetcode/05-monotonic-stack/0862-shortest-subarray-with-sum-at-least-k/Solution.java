class Solution {
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        int preSum = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int ans = n + 1;
        
        for (int i = 0; i < n; i++) {
            if (preSum <= 0) {
                preSum = 0;
                map.clear();
            }
            while (map.ceilingEntry(preSum) != null) {
                map.remove(map.ceilingKey(preSum));
            }
            map.put(preSum, i - 1);
            int currSum = nums[i] + preSum;
            if (currSum >= k)
                ans = Math.min(ans, i - map.get(map.floorKey(currSum - k)));
            preSum = currSum;
        }
        return ans == n + 1 ? -1 : ans;
    }
}