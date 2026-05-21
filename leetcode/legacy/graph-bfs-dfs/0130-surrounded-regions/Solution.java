class Solution {
    int[][] dir = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public void solve(char[][] board) {
        Queue<int[]> queue = new LinkedList<>();
        int n = board.length;
        int m = board[0].length;

        for(int i = 0; i < n; i++) {
            if(board[i][0] == 'O') {
                board[i][0] = '#';
                queue.offer(new int[]{i, 0});
            }
            if(board[i][m - 1] == 'O') {
                board[i][m - 1] = '#';
                queue.offer(new int[]{i, m - 1});
            }
        }
        for(int i = 0; i < m; i++) {
            if(board[0][i] == 'O') {
                board[0][i] = '#';
                queue.offer(new int[]{0, i});
            }
            if(board[n - 1][i] == 'O') {
                board[n - 1][i] = '#';
                queue.offer(new int[]{n - 1, i});
            }
        }

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            for(int index = 0; index < 4; index++) {
                int ni = i + dir[index][0];
                int nj = j + dir[index][1];
                if(ni < 0 ||
                   ni >= n ||
                   nj < 0 ||
                   nj >= m ||
                   board[ni][nj] == 'X' ||
                   board[ni][nj] == '#') continue;
                board[ni][nj] = '#';
                queue.offer(new int[]{ni, nj});
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == 'O') board[i][j] = 'X';
                if(board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }
}