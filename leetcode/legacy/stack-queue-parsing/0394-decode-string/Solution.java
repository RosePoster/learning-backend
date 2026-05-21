class Solution {
    private int index = 0;
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        while(index < s.length() && s.charAt(index) != ']') {
            char c = s.charAt(index);
            if(!isNum(c)) sb.append(c);
            else {
                int time = 0;
                while(isNum(s.charAt(index))) {
                    time *= 10;
                    time += s.charAt(index) - '0';
                    index++;
                }
                index++; // 跳过'['
                String subString = decodeString(s);
                for(int i = 0; i < time; i++) sb.append(subString);
            }
            index++;
        }
        return sb.toString();
    }

    private boolean isNum(char c) {
        return c - '0' < 10 && c - '0' >= 0;
    } 
}