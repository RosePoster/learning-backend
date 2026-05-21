class Solution {
    public int openLock(String[] deadends, String target) {
        int nTarget = Integer.parseInt(target);
        int[][] dir = {{-1, 0, 0, 0}, {1, 0, 0, 0},
                       {0, -1, 0, 0}, {0, 1, 0, 0}, 
                       {0, 0, -1, 0}, {0, 0, 1, 0},
                       {0, 0, 0, -1}, {0, 0, 0, 1}};
        Set<Integer> set = new HashSet<>();
        for(String s : deadends) set.add(Integer.parseInt(s));
        Queue<int[]> queue = new LinkedList<>();
        int[][][][] dists = new int[10][10][10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                for(int k = 0; k < 10; k++)
                    Arrays.fill(dists[i][j][k], -1); 
        dists[0][0][0][0] = 0;
        queue.offer(new int[]{0, 0, 0, 0});

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            if(set.contains(curr[0] * 1000 + curr[1] * 100 + curr[2] * 10 + curr[3])) continue;
            int currDist = dists[curr[0]][curr[1]][curr[2]][curr[3]];
            if(currDist == -1) continue;
            for(int i = 0; i < 8; i++) {
                int one = helper(curr[0] + dir[i][0]);
                int two = helper(curr[1] + dir[i][1]);
                int three = helper(curr[2] + dir[i][2]);
                int four = helper(curr[3] + dir[i][3]);
                int nextDist = dists[one][two][three][four];
                if(nextDist == -1 || nextDist > currDist + 1) {
                    dists[one][two][three][four] = currDist + 1;
                    queue.offer(new int[]{one, two, three, four});
                }
            }
        }
        return dists[nTarget / 1000][(nTarget / 100) % 10][(nTarget / 10) % 10][nTarget % 10];
    }

    private int helper(int n) {
        if(n == -1) return 9;
        if(n == 10) return 0;
        return n;
    }
}
