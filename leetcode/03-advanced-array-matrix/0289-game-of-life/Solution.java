class Solution {
    private int[][] dirs = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
        {1, 1}, {1, 0}, {1, -1}, {0, -1}
    };

    public void gameOfLife(int[][] board) {
        int n = board.length;
        int m = board[0].length;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                int liveNerb = 0;
                for(int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if(ni < 0 || ni >= n ||
                       nj < 0 || nj >= m   
                    ) continue;
                    if(Math.abs(board[ni][nj]) == 1) liveNerb++;
                }
                if(board[i][j] == 1 && (liveNerb < 2 || liveNerb > 3)) {
                    board[i][j] = -1;
                } else if(board[i][j] == 0 && liveNerb == 3) board[i][j] = 2;
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == -1) board[i][j] = 0;
                if(board[i][j] == 2) board[i][j] = 1;
            }
        }
    }
}