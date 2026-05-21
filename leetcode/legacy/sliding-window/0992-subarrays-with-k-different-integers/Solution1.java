class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        int redundantPre = 0;
        int left = 0;
        int ans = 0;
        int right = 0;
        int diffNums = 0;

        for(; right < nums.length; right++) {
            int count = counts.getOrDefault(nums[right], 0);
            if(count == 0) diffNums++;
            counts.put(nums[right], count + 1);
            
            // 如果出现了一个新的数（与窗口内的数均不同），left直接左移即可移出一个不同的数
            if(diffNums > k) {
                counts.merge(nums[left++], -1, Integer::sum);
                diffNums--;
                // rudundantPre重新指向新的冗余
                redundantPre = left;
            }
            
            // 压缩冗余
            // 无论是否出现新的数，都要尝试压缩冗余
            while(counts.get(nums[left]) > 1) {
                counts.merge(nums[left++], -1, Integer::sum);
            }

            if(diffNums == k) ans += left - redundantPre + 1;
        }
        return ans;
    }
}