class Solution {
    public int maxVowels(String s, int k) {
        int right = 0;
        int curr = 0;
        for(; right < k; right++) {
            if(isVowel(s.charAt(right))) curr++;
        }
        int ans = curr;

        for(; right < s.length(); right++) {
            if(isVowel(s.charAt(right))) curr++;
            if(isVowel(s.charAt(right - k))) curr--;
            ans = Math.max(ans, curr);
            if(ans == k) return ans;
        }
        return ans;
    }

    private boolean isVowel(char c) {
        return c == 'a' ||
               c == 'e' ||
               c == 'i' ||
               c == 'o' ||
               c == 'u';
    }
}