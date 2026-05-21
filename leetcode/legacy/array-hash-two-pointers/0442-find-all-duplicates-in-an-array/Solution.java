class Solution { 
    public List<Integer> findDuplicates(int[] nums) { 
        List<Integer> ans = new ArrayList<>(); 
        for(int i = 0; i < nums.length; i++) { 
            int num = nums[i] - 1; 
            if(num != i) { 
                if(num + 1 == nums[num]) { 
                    // 收集答案 
                    ans.add(num + 1);
                }else { 
                    // 交换 
                    nums[i] = nums[num]; 
                    nums[num] = num + 1; 
                    // 换到未遍历过的元素，不能遍历下一个。 
                    if(num > i) i--; 
                } 
            } 
        } 
        return ans; 
    } 
}