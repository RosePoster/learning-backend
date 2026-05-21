class Solution {
    private int[][] dir = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    private int m;
    private int n;
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        m = heights.length;
        n = heights[0].length;

        boolean[][] toPO = new boolean[m][n];
        Queue<int[]> queuePO = new LinkedList<>();
        for(int i = 0; i < m; i++) {
            toPO[i][0] = true;
            queuePO.offer(new int[]{i, 0});
        }
        for(int i = 1; i < n; i++){
            toPO[0][i] = true;
            queuePO.offer(new int[]{0, i});
        }   
        bfs(heights, queuePO, toPO);

        boolean[][] toAO = new boolean[m][n];
        Queue<int[]> queueAO = new LinkedList<>();
        for(int i = 0; i < m; i++) {
            toAO[i][n - 1] = true;
            queueAO.offer(new int[]{i, n - 1});
        }
        for(int i = 0; i < n - 1; i++){
            toAO[m - 1][i] = true;
            queueAO.offer(new int[]{m - 1, i});
        }   
        bfs(heights, queueAO, toAO);

        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(toAO[i][j] && toPO[i][j]) ans.add(List.of(i, j));
            }
        }
        return ans;
    }

    private void bfs(int[][] heights, Queue<int[]> queue, boolean[][] toOcean) {
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            for(int index = 0; index < 4; index++) {
                int in = i + dir[index][0];
                int jn = j + dir[index][1];
                if(in < 0 ||
                   in >= m ||
                   jn < 0 ||
                   jn >= n) continue;
                if(toOcean[in][jn]) continue;
                if(heights[in][jn] >= heights[i][j]) {
                    toOcean[in][jn] = true;
                    queue.offer(new int[]{in, jn});
                }
            }
        }
    }
}