class Solution {
    public int hIndex(int[] citations) {
        int len = 0;
        for(int citation : citations) len = Math.max(citation, len);
        len = Math.min(len, citations.length);
        int[] freq = new int[len + 1];
        for(int citation : citations) freq[Math.min(citation, len)]++;
        for(int i = len; i > 0; i--) {
            if(freq[i] >= i) return i;
            freq[i - 1] += freq[i];
        }
        return 0;
    }
}