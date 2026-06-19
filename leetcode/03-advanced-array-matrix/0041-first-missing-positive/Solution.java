class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            while(nums[i] > 0 && nums[i] <= n 
                  && nums[i] != i + 1 
                  && nums[nums[i] - 1] != nums[i]
                ) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;            
            }
            if(nums[i] != i + 1) nums[i] = 0;
        }

        int ans = 0;
        for(; ans < n; ans++) if(nums[ans] == 0) break;
        
        return ans + 1;
    }
}