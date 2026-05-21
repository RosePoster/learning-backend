class Solution {
    public int snakesAndLadders(int[][] board) {
        int len = board.length;
        int ans = 1;
        int end = len * len;
        Queue<Integer> queue = new LinkedList<>();
        board[len - 1][0] = 0;
        queue.offer(1);
        while(!queue.isEmpty()) {
            int qLen = queue.size();
            for(int index = 0; index < qLen; index++) {
                int curr = queue.poll();
                for(int next = curr + 1; next <= Math.min(curr + 6, end); next++) {
                    int[] nextIndex = num2index(next, len);
                    int i = nextIndex[0];
                    int j = nextIndex[1];
                    if(board[i][j] == end || next == end) return ans;
                    if(board[i][j] == 0) continue;
                    if(board[i][j] == -1) queue.offer(next);
                    else queue.offer(board[i][j]);
                    board[i][j] = 0;
                }
            }
            ans++;
        }
        return -1;
    }

    private int[] num2index(int n, int len) {
        int i = len - 1 - (n - 1) / len;
        int j = (len % 2 == i % 2) ? len - 1 - ((n - 1) % len) : (n - 1) % len;
        return new int[]{i, j};
    }
}
