class Solution {
    public int compareVersion(String version1, String version2) {
        int[] idx1 = new int[1];
        int[] idx2 = new int[1];
        int n = version1.length();
        int m = version2.length();
        int ver1 = 0;
        int ver2 = 0;
        
        while (ver1 == ver2 && (idx1[0] < n || idx2[0] < m)) {
            ver1 = getVer(version1, idx1);
            ver2 = getVer(version2, idx2);
        }

        int ans = ver1 - ver2;
        ans = Math.max(ans, -1);
        ans = Math.min(ans, 1);

        return ans;
    }

    private int getVer(String s, int[] idx) {
        int n = s.length();
        int num = 0;
        while (idx[0] < n && s.charAt(idx[0]) == '0') idx[0]++;
        while (idx[0] < n) {
            char c = s.charAt(idx[0]);
            idx[0]++;
            if(c == '.') break;
            num *= 10;
            num += c - '0';
        }
        return num;
    }
}