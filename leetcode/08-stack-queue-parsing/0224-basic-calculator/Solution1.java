class Solution {

    private int index = 0;
    
    public int calculate(String s) {
        int ans = count(s);

        while(index < s.length()) {
            char c = s.charAt(index++); 
            int curr = count(s);
            if(c == '+') ans += curr;
            else ans -= curr;
        }

        return ans;
    }

    // count 停在')'后，或者退化为s2n
    private int count(String s) {
        while(index < s.length() && s.charAt(index) == ' ') index++; // 跳过左括号间空格
        if(index >= s.length() || s.charAt(index) != '(') return s2n(s);
        index++;
        int term = count(s);

        while(index < s.length()) {
            char c = s.charAt(index++); 
            if(c == ' ') continue; // 跳过右括号间空格
            if(c == ')') return term;
            int curr = count(s);
            if(c == '+') term += curr;
            else term -= curr;
        }

        return term;
    }

    // s2n停在算术符号前，并跳过所有数字左右的空格
    private int s2n(String s) {
        int num = 0;

        for(; index < s.length(); index++) {
            char c = s.charAt(index);
            if(c == ' ') continue;
            if(c > '9' || c < '0') return num;
            num *= 10;
            num += c - '0';
        }

        return num;
    }
}