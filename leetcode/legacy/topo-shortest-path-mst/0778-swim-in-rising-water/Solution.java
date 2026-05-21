class Solution {
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int[][] minArriveTime = new int[n][n];
        for(int i = 0; i < n; i++) Arrays.fill(minArriveTime[i], Integer.MAX_VALUE);
        boolean[][] visited = new boolean[n][n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            Comparator.comparingInt(a -> a[2])
        );
        pq.offer(new int[]{0, 0, grid[0][0]});
        minArriveTime[0][0] = grid[0][0];

        while(!pq.isEmpty() && !visited[n - 1][n - 1]) {
            int[] node = pq.poll();
            int i = node[0];
            int j = node[1];
            if(visited[i][j]) continue;
            visited[i][j] = true;
            int w = node[2];

            for(int[] dir : dirs) {
                int ni = i + dir[0];
                int nj = j + dir[1];

                // 若ni、nj不合法，或minArrveTime已不能再优，或w不能更新minArrveTime，跳过。
                if(ni < 0 || ni >= n ||
                   nj < 0 || nj >= n ||
                   visited[ni][nj] ||
                   minArriveTime[ni][nj] == grid[ni][nj] ||
                   w >= minArriveTime[ni][nj]) continue;
                
                // 将w转化为合法值，更新minArriveTime，更新优先队列。
                int nw = Math.max(grid[ni][nj], w);
                minArriveTime[ni][nj] = nw;
                pq.offer(new int[]{ni, nj, nw});
            }
        }

        return minArriveTime[n - 1][n - 1];
    }
}