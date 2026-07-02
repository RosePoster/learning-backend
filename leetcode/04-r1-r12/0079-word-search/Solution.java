class Solution {
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0) && search(board, word, i, j, 0)) return true; 
            }
        }
        return false;
    }
    
    private boolean search(char[][] board, String word, int i, int j, int curr) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || word.charAt(curr) != board[i][j]) return false;
        if (curr == word.length() - 1) return true;
        char c = board[i][j];
        board[i][j] = '#';
        for (int[] dir : dirs) {
            if (search(board, word, i + dir[0], j + dir[1], curr + 1)) {
                board[i][j] = c;
                return true;
            }
        }
        board[i][j] = c;
        return false;
    }
}