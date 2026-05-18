class Solution {
    private int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; 

    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int cost = 0;

        Queue<int[]> queue = new LinkedList<>();
        visit(0, 0, visited, queue, grid);

        while(!visited[n - 1][m - 1]) {
            cost++;
            int len = queue.size();
            for(int index = 0; index < len; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                for(int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    visit(ni, nj, visited, queue, grid);
                }
            }
        }

        return cost;
    }

    private void visit(int i, int j, boolean[][] visited, Queue<int[]> queue, int[][] grid) {
        while(i >= 0 && i < grid.length && 
              j >= 0 && j < grid[0].length && 
              !visited[i][j]
            ) {
            visited[i][j] = true;
            queue.offer(new int[]{i, j});

            int[] dir = dirs[grid[i][j] - 1];
            i += dir[0];
            j += dir[1];
        }
    }
}