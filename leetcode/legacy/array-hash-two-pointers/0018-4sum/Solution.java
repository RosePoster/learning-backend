class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if(nums.length < 4) return ans;

        Arrays.sort(nums);

        for(int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) continue;
            if((long)nums[i] * 4 > target) break;
            for(int j = i + 1; j < nums.length; j++) {
                if(j > i + 1 && nums[j] == nums[j - 1]) continue;
                int begin = j + 1;
                int end = nums.length - 1;
                long restTarget = (long)target - nums[i] - nums[j];
                while(begin < end) {
                    int curr = nums[begin] + nums[end]; // 题设给出：“-10^9 <= nums[i] <= 10^9”，因此int安全
                    if(restTarget == curr) {
                        ans.add(List.of(nums[i], nums[j], nums[begin], nums[end]));
                        begin++;
                        end--;
                        while(begin < end && nums[begin] == nums[begin - 1]) begin++;
                        while(begin < end && nums[end] == nums[end + 1]) end--;
                    } else if(restTarget > curr) {
                        begin++;
                    } else {
                        end--;
                    }
                }
            }
        }

        return ans;
    }
}