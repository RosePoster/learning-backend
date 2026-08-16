class Solution {
    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int maxLen = 0;
        int[] currValue = new int[m];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '0') currValue[j] = 0;
                else currValue[j]++;

                while (!stack.isEmpty() && currValue[j] < currValue[stack.peek()]) {
                    int high = currValue[stack.pop()];
                    int left = !stack.isEmpty() ? stack.peek() : -1;
                    int len = Math.min(j - left - 1, high);
                    maxLen = Math.max(maxLen, len);
                }
                stack.push(j);
            }

            while (!stack.isEmpty()) {
                int high = currValue[stack.pop()];
                int left = !stack.isEmpty() ? stack.peek() : -1;
                int len = Math.min(m - left - 1, high);
                maxLen = Math.max(maxLen, len);
            }
        }
        
        return maxLen * maxLen;
    }
}