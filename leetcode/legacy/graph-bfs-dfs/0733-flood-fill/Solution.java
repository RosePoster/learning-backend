class Solution {
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(color == image[sr][sc]) return image;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        int aim = image[sr][sc];
        image[sr][sc] = color;
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            for(int[] dir : dirs) {
                int in = i + dir[0];
                int jn = j + dir[1];
                if(in < 0 || in >= image.length ||
                   jn < 0 || jn >= image[0].length ||
                   image[in][jn] != aim) continue;
                   image[in][jn] = color;
                   queue.offer(new int[]{in, jn});
            }
        }
        return image;
    }
}