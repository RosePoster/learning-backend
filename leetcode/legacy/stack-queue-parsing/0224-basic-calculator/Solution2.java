class Solution {
    private int index = 0;

    public int calculate(String s) {
        int ans = parseValue(s);

        while(index < s.length()) {
            char c = s.charAt(index++);
            int curr = 0;
            
            if(c == ' ') continue;
            if(c == ')') break;
            curr = parseValue(s);

            if(c == '+') ans += curr;
            else ans -= curr;
        }    

        return ans;
    }
    
    private int parseValue(String s) {
        int num = 0;
        while(index < s.length() && s.charAt(index) == ' ') index++;
        if(index >= s.length()) return 0;
        if(s.charAt(index) == '(') {
            index++;
            return calculate(s);
        }
        for(; index < s.length(); index++) {
            char c = s.charAt(index);
            if(c == ' ') continue;
            if(c > '9' || c < '0') break;
            num *= 10;
            num += c - '0';
        }
        return num;
    }
}