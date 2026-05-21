class Solution {
    public int nearestExit(char[][] maze, int[] entrance) {
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0 ,-1}, {0, 1}};
        int n = maze.length;
        int m = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(entrance);
        maze[entrance[0]][entrance[1]] = '#';
        int dist = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int index = 0; index < size; index++) {
                int[] curr = queue.poll();
                int i = curr[0];
                int j = curr[1];
                for(int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if(ni < 0 || ni >= n ||
                       nj < 0 || nj >= m ||
                       maze[ni][nj] != '.') continue;
                    if(ni == n - 1 || ni == 0 ||
                       nj == m - 1 || nj == 0) return dist;
                    queue.offer(new int[]{ni, nj});
                    maze[ni][nj] = '#';
                }
            }
            dist++;
        }
        return -1;
    }
}