class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queueCurr = new LinkedList<>();
        Queue<int[]> queueNext = new LinkedList<>();
        int min = 0;
        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 2) {
                    queueCurr.offer(new int[]{i, j});
                }
                if(grid[i][j] == 1) {
                    count++;
                }
            }
        }

        while(!queueCurr.isEmpty()) {
            int[] curr = queueCurr.poll();
            int i = curr[0];
            int j = curr[1];

            if(i - 1 >= 0 && grid[i - 1][j] == 1) {
                grid[i - 1][j] = 2;
                queueNext.offer(new int[]{i - 1, j});
                count--;
            }
            if(j - 1 >= 0 && grid[i][j - 1] == 1) {
                grid[i][j - 1] = 2;
                queueNext.offer(new int[]{i, j - 1});
                count--;
            }
            if(i + 1 < grid.length && grid[i + 1][j] == 1) {
                grid[i + 1][j] = 2;
                queueNext.offer(new int[]{i + 1, j});
                count--;
            }
            if(j + 1 < grid[0].length && grid[i][j + 1] == 1) {
                grid[i][j + 1] = 2;
                queueNext.offer(new int[]{i, j + 1});
                count--;
            }
            if(queueCurr.isEmpty() && !queueNext.isEmpty()) {
                Queue<int[]> temp = queueCurr;
                queueCurr = queueNext;
                queueNext = temp;
                min++;
            }
        }
        
        return count == 0 ? min : -1;
    }
}