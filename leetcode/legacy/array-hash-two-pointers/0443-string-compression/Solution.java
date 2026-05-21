class Solution {
    public int compress(char[] chars) {
        int write = 0;
        int read = 0;
        int len = chars.length;
        while(read < len) {
            char ch = chars[read];
            int freq = 0;
            while(read < len && ch == chars[read]) {
                freq++;
                read++;
            }
            // 收集
            chars[write++] = ch;
            if(freq > 1) {
                char[] s = Integer.toString(freq).toCharArray();
                for(char c : s) {
                    chars[write++] = c;
                }
            }
        }
        return write;
    }
}