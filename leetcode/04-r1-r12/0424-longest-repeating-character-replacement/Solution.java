class Solution {
    public int characterReplacement(String s, int k) {
        int ans = 0;
        int left = 0;
        int right = 0;
        int[] freqs = new int[26];
        Map<Integer, Set<Integer>> buckets = new HashMap<>();
        for(int i = 0; i <= k + 1; i++) buckets.put(i, new HashSet<>());
        for(int i = 0; i < 26; i++) buckets.get(0).add(i);
        int maxFreq = 0;

        while (right < s.length()) {
            int addC = s.charAt(right++) - 'A';
            int addCFreq = ++freqs[addC];
            if (addCFreq >= 1 && addCFreq <= k + 1) {
                buckets.get(addCFreq - 1).remove(addC);
                buckets.get(addCFreq).add(addC);
            }
            maxFreq = Math.max(addCFreq, maxFreq);
            while (right - left - maxFreq > k) {
                int removeC = s.charAt(left++) - 'A';
                int removeCFreq = --freqs[removeC];
                if (removeCFreq >= 0 && removeCFreq <= k) {
                    buckets.get(removeCFreq + 1).remove(removeC);
                    buckets.get(removeCFreq).add(removeC);
                } 
                if (removeCFreq + 1 == maxFreq && 
                    (buckets.get(maxFreq) == null ||
                    buckets.get(maxFreq).isEmpty())
                ) maxFreq--;
                
            }
            ans = Math.max(ans, right - left);
        }

        return ans;
    }
}