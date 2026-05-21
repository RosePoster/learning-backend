class Solution {
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int n;
    public int shortestBridge(int[][] grid) {
        n = grid.length;
        Queue<int[]> queue = findEdge(grid, findLand(grid)); 
        int dist = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int index = 0; index < size; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                for(int[] dir : dirs) {
                    int in = i + dir[0];
                    int jn = j + dir[1];
                    if(in < 0 || in >= n ||
                       jn < 0 || jn >= n ||
                       grid[in][jn] < 0) continue;
                    if(grid[in][jn] == 1) return dist;
                    grid[in][jn] = -1;
                    queue.offer(new int[]{in, jn});
                }
            }
            dist++;
        }
        return -1;
    }

    private int[] findLand(int[][] grid) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private Queue<int[]> findEdge(int[][] grid, int[] begin) {
        Queue<int[]> edge = new LinkedList<>();
        Queue<int[]> queue = new LinkedList<>();
        edge.offer(new int[]{begin[0], begin[1]});
        queue.offer(new int[]{begin[0], begin[1]});
        grid[begin[0]][begin[1]] = -2;
        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            for(int[] dir : dirs) {
                int in = i + dir[0];
                int jn = j + dir[1];
                if(in < 0 || in >= n ||
                   jn < 0 || jn >= n ||
                   grid[in][jn] == 0) {
                    if(grid[i][j] != -2) {
                        grid[i][j] = -2;
                        edge.offer(curr);
                    }
                    continue;
                }
                if(grid[in][jn] == 1) {
                    queue.offer(new int[]{in, jn});
                    grid[in][jn] = -1;
                }
            }
        }
        return edge;
    }
}