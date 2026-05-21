class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        int leftCount = 0;
        int rightCount = 0;
        int currLeft = 0;
        int misMatch = 0;

        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                leftCount++;
                currLeft++;
            }
            if(c == ')') {
                rightCount++;
                if(currLeft == 0) misMatch++;
                else currLeft--;
            }
        }

        int deleteRight = misMatch;
        rightCount -= deleteRight;
        if (leftCount > rightCount) {
            leftCount = rightCount;
        } else {
            deleteRight += rightCount - leftCount;
        }

        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(c == ')' && deleteRight > 0) {
                deleteRight--;
                continue;
            }
            if(c == '(') {
                if(leftCount == 0) continue;
                leftCount--;
            }
            sb.append(c);
        }

        return sb.toString();
    }
}