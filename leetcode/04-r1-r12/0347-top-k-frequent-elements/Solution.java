class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> freqs = new HashMap<>();
        for(int num : nums) freqs.merge(num, 1, Integer::sum);
        List<Integer>[] freqsBucket = new List[n + 1];
        for(int i = 0; i < n + 1; i++) freqsBucket[i] = new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry : freqs.entrySet()) 
            freqsBucket[entry.getValue()].add(entry.getKey());

        int[] ans = new int[k];
        int i = 0;
        for(int j = n; j >= 0 && i < k; j--) {
            List<Integer> bucket = freqsBucket[j]; 
            for(int num : bucket) ans[i++] = num; // 题目保证答案唯一，所以单个桶内不会出现溢出
        }

        return ans;
    } 
}