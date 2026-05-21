class Solution {
    private int index;
    public int calculate(String s) {
        index = 0;
        int ans = getCountedNum(s); // getCountedNum保证index停在加减运算符位置
        while(index < s.length()) {
            char ope = s.charAt(index++);            
            int num = getCountedNum(s);
            if(ope == '+') ans += num;
            else ans -= num;
        }
        return ans;
    }

    private int getCountedNum(String s) {
        int num1 = s2n(s); // s2n 保证index停留在运算符位置
        while(index < s.length()) {
            char ope = s.charAt(index);
            if(ope == '+' || ope == '-') return num1;
            index++;
            int num2 = s2n(s);
            if(ope == '*') num1 *= num2;
            else num1 /= num2;
        }
        return num1;
    }

    private int s2n(String s) {
        int num = 0;
        for(; index < s.length(); index++) {
            char c = s.charAt(index);
            if(c == ' ') continue; // 这里会跳过所有空格
            if(c < '0' || c > '9') break;
            num *= 10;
            num += c - '0';
        }
        return num;
    }
}