class Solution {
    public String removeDuplicateLetters(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        boolean[] inStack = new boolean[26];
        int[] lastIndex = new int[26];
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++) lastIndex[s.charAt(i) - 'a'] = i;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(inStack[c - 'a']) continue;
            while(!stack.isEmpty() && c < stack.peek() && lastIndex[stack.peek() - 'a'] > i) {
                inStack[stack.pop() - 'a'] = false;
            }
            stack.push(c);
            inStack[c - 'a'] = true;
        }
        while(!stack.isEmpty()) sb.append(stack.pop());
        
        return sb.reverse().toString();
    }
}