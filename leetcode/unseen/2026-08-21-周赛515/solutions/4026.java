class Solution {
    public int maximumGap(String skill, String station) {
        int n = skill.length();
        int m = station.length();
        int[] left = new int[n];
        int skillidx = 0;

        for (int i = 0; i < m && skillidx < n; i++) {
            if (station.charAt(i) == skill.charAt(skillidx)) {
                left[skillidx] = i;
                skillidx++;
            }
        }

        skillidx = n - 1;
        int ans = 0;
        for (int i = m - 1; i >= 0 && skillidx > 0; i--) {
            if (station.charAt(i) == skill.charAt(skillidx)) {
                skillidx--;
                ans = Math.max(ans, i - left[skillidx]);
            }
        }

        return ans;
    }
}