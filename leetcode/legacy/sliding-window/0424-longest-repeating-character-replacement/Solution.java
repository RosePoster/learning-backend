class Solution {
    public int characterReplacement(String s, int k) {
        int left = 0;
        int[] counts = new int[26];
        int maxCount = 0;
        int ans = 0;

        for(int right = 0; right < s.length(); right++) {
            // 拓展窗口
            int c = s.charAt(right) - 'A'; 
            counts[c]++;
            maxCount = Math.max(maxCount, counts[c]);

            // 收缩窗口
            while(right - left + 1 - maxCount > k) {
                // 这里的while可以改成if，因为继续缩小窗口，不会产生全局最优解
                counts[s.charAt(left) - 'A']--;
                left++;
            }

            // 收集答案
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}
