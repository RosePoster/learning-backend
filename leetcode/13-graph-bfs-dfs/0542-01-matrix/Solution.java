class Solution {
    public int[][] updateMatrix(int[][] mat) {
        Queue<int[]> queue = new LinkedList<>();
        int[][] ans = new int[mat.length][mat[0].length];
        for(int i = 0; i < mat.length; i++) {
            Arrays.fill(ans[i], Integer.MAX_VALUE);
        }

        for(int i = 0; i < mat.length; i++) {
            for(int j = 0; j < mat[0].length; j++) {
                if(mat[i][j] == 1) {
                    if(i - 1 >= 0 && mat[i - 1][j] == 0 ||
                       i + 1 < mat.length && mat[i + 1][j] == 0 ||
                       j - 1 >= 0 && mat[i][j - 1] == 0 ||
                       j + 1 < mat[0].length && mat[i][j + 1] == 0
                    ) { 
                        ans[i][j] = 1;
                        queue.offer(new int[]{i, j});
                    }
                }else ans[i][j] = 0;
            }
        }

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            int dist = ans[i][j] + 1;
            if(i - 1 >= 0 && ans[i - 1][j] > dist) {
                ans[i - 1][j] = dist;
                queue.offer(new int[]{i - 1, j});
            }
            if(i + 1 < mat.length && ans[i + 1][j] > dist) {
                ans[i + 1][j] = dist;
                queue.offer(new int[]{i + 1, j});
            }
            if(j - 1 >= 0 && ans[i][j - 1] > dist) {
                ans[i][j - 1] = dist;
                queue.offer(new int[]{i, j - 1});
            }
            if(j + 1 < mat[0].length && ans[i][j + 1] > dist) {
                ans[i][j + 1] = dist;
                queue.offer(new int[]{i, j + 1});
            }
        }

        return ans;
    }
}