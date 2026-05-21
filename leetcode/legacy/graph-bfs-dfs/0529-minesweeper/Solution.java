class Solution {
    private int[][] dir = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
                                      {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    public char[][] updateBoard(char[][] board, int[] click) {
        if(board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        Queue<int[]> queue = new LinkedList<>();
        int n = board.length;
        int m = board[0].length;
        board[click[0]][click[1]] = 'B';
        queue.offer(click);
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            char count = '0';
            List<int[]> nextList = new ArrayList<>();
            for(int index = 0; index < 8; index++) {
                int ni = i + dir[index][0];
                int nj = j + dir[index][1];
                if(ni < 0 || ni >= n ||
                   nj < 0 || nj >= m) continue;
                if(board[ni][nj] == 'E') nextList.add(new int[]{ni, nj});
                if(board[ni][nj] == 'M') count++;
            }
            if(count != '0') {
                board[i][j] = count;
                continue;
            }
            for(int[] next : nextList) {
                board[next[0]][next[1]] = 'B';
                queue.offer(next);
            }
        }
        return board;
    }
}