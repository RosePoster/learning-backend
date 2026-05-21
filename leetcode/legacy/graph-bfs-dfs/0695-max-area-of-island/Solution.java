class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 0) continue;
                ans = Math.max(ans, search(grid, i, j));
            }
        }
        return ans;
    }

    private int search(int[][] grid, int iIndex, int jIndex) {
        Queue<int[]> queue = new LinkedList<>();    
        int area = 0;
        grid[iIndex][jIndex] = 0;
        queue.offer(new int[]{iIndex, jIndex});
        while(!queue.isEmpty()) {
            int[] currNode = queue.poll();
            area++;
            int i = currNode[0];
            int j = currNode[1];
            if(i - 1 >= 0 && grid[i - 1][j] == 1) {
                grid[i - 1][j] = 0;
                queue.offer(new int[]{i - 1, j});
            }
            if(i + 1 < grid.length && grid[i + 1][j] == 1) {
                grid[i + 1][j] = 0;
                queue.offer(new int[]{i + 1, j});
            }
            if(j - 1 >= 0 && grid[i][j - 1] == 1) {
                grid[i][j - 1] = 0;
                queue.offer(new int[]{i, j - 1});
            }
            if(j + 1 < grid[0].length && grid[i][j + 1] == 1) {
                grid[i][j + 1] = 0;
                queue.offer(new int[]{i, j + 1});
            }
        }
        return area;
    }
}