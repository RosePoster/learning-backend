class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()) return false;
        int[] count = new int[26];
        int unsatisfied = 0;
        int right = 0;

        // Initialize the count array with the first window of s2 and the characters of s1
        for(int i = 0; i < s1.length(); i++) {
            count[s1.charAt(i) - 'a']--;
            count[s2.charAt(i) - 'a']++;
        }
    
        // Count the number of characters that are not satisfied (i.e., count[i] != 0)
        for(int i = 0; i < count.length; i++) {
            if(count[i] != 0) unsatisfied++;
        }

        // Slide the window over s2 and update the count array and unsatisfied count accordingly
        for(int i = s1.length(); i < s2.length(); i++) {
            if(unsatisfied == 0) return true;    
            int leftC = s2.charAt(i - s1.length()) - 'a';
            int rightC = s2.charAt(i) - 'a';

            count[leftC]--;
            if(count[leftC] == 0) unsatisfied--;
            if(count[leftC] == -1) unsatisfied++;

            count[rightC]++;
            if(count[rightC] == 0) unsatisfied--;
            if(count[rightC] == 1) unsatisfied++;
        }

        // Check the last window after the loop
        return unsatisfied == 0;
    }
}