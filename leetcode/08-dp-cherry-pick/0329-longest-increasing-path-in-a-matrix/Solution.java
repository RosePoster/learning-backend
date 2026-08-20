class Solution {
    private int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int ans = 0;
        // 0表示未处理，正数表示该点作为起点最长路径长度
        int[][] longestPath = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (longestPath[i][j] == 0) {
                    find(matrix, longestPath, i, j);
                }
                ans = Math.max(ans, longestPath[i][j]);
            }
        }

        return ans;
    }

    private void find(int[][] matrix, int[][] longestPath, int i, int j) {
        int nextMax = 0;
        int currVal = matrix[i][j];
        for (int[] dir : dirs) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            if (ni < 0 || ni >= matrix.length ||
                nj < 0 || nj >= matrix[0].length
            ) continue;
            
            if (currVal < matrix[ni][nj]) {
                if (longestPath[ni][nj] == 0)
                    find(matrix, longestPath, ni, nj);
                nextMax = Math.max(nextMax, longestPath[ni][nj]);
            }
        }
        longestPath[i][j] = nextMax + 1;
    }
}