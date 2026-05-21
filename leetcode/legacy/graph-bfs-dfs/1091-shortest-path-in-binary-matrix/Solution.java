class Solution {
    private int[][] dirs = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
        {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0] == 1 || grid[n - 1][n - 1] == 1) return -1;
        if(n == 1) return 1;
        Queue<int[]> queue = new LinkedList<>();
        grid[0][0] = 1;
        queue.offer(new int[]{0, 0});
        int ans = 2;
        while(!queue.isEmpty()) {
            int len = queue.size();
            for(int index = 0; index < len; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                for(int[] dir : dirs) {
                    int in = i + dir[0];
                    int jn = j + dir[1];
                    if(in < 0 || in >= n ||
                       jn < 0 || jn >= n ||
                       grid[in][jn] == 1) continue;
                    if(in == n - 1 && jn == n - 1) return ans;
                    grid[in][jn] = 1;
                    queue.offer(new int[]{in, jn});
                }       
            }
            ans++;
        }
        return -1;
    }
}