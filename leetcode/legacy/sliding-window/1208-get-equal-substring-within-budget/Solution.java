class Solution {
    public int equalSubstring(String s, String t, int maxCost) {
        int left = 0;
        int cost = 0;

        for(int right = 0; right < t.length(); right++) {
            cost += Math.abs(s.charAt(right) - t.charAt(right));
            if(cost > maxCost) {
                cost -= Math.abs(s.charAt(left) - t.charAt(left));
                left++;
            }
        }
        
        return t.length() - left;
    }
}