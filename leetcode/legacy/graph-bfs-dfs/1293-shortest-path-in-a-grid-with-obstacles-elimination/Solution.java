class Solution {    
    public int shortestPath(int[][] grid, int k) {
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int n = grid.length;
        int m = grid[0].length;
        int[][] visited = new int[n][m];
        for(int i = 0; i < n; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, grid[0][0]});
        visited[0][0] = grid[0][0];
        int dist = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int index = 0; index < size; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                int cost = curr[2];
                if(cost > k) continue;
                if(i == n - 1 && j == m - 1) return dist;
                for(int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if(ni < 0 || ni >= n ||
                       nj < 0 || nj >= m) continue;
                    int nCost = cost + grid[ni][nj];
                    if(nCost >= visited[ni][nj]) continue;
                    visited[ni][nj] = nCost;
                    queue.offer(new int[]{ni, nj, nCost});
                }
            }
            dist++;
        }
        return -1;
    }
}
