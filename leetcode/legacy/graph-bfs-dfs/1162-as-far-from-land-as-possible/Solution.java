class Solution {
    public int maxDistance(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int ans = -1;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j <grid[0].length; j++) {
                if(grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int dist = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int index = 0; index < size; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                for(int[] dir : dirs){
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if( ni >= 0 &&
                        ni < grid.length &&
                        nj >= 0 &&
                        nj < grid[0].length &&
                        grid[ni][nj] == 0 ) {
                        queue.offer(new int[]{ni, nj});
                        grid[ni][nj] = 1;
                        ans = Math.max(ans, dist);
                    } 
                }
            }
            dist++;
        }
        return ans;
    }
}