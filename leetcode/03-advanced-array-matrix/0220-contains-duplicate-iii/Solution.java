class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        TreeMap<Long, Integer> map = new TreeMap<>();
        int n = nums.length;
        map.put((long)nums[0], 1);
        for(int i = 1; i < n; i++) {
            int curr = nums[i];
            int j = i - indexDiff;
            Long ceil = map.ceilingKey((long)curr - valueDiff);
            if(ceil != null && ceil <= (long)curr + valueDiff) return true;
            map.merge((long)curr, 1, Integer::sum);
            if(j >= 0) {
                long removeNum = (long)nums[j];
                int cnt = map.get(removeNum);
                if(cnt == 1) map.remove(removeNum);
                else map.put(removeNum, cnt - 1);
            }
        }

        return false;
    }
}