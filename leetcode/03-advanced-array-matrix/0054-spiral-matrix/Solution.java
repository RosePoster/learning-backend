class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        List<Integer> collector = new ArrayList<>();

        for(int i = 0; i < (Math.min(m, n) + 1) / 2; i++) layerOrder(i, matrix, collector);

        return collector;
    }

    public void layerOrder(int layer, int[][] matrix, List<Integer> collector) {
        int n = matrix.length;
        int m = matrix[0].length;
        int top = layer;
        int bottom = n - 1 - layer;
        int left = layer;
        int right = m - 1 - layer;

        if (top == bottom) {
            for(int i = left; i <= right; i++) collector.add(matrix[top][i]);
        } else if (left == right) {
            for(int i = top; i <= bottom; i++) collector.add(matrix[i][left]);
        } else {
            for(int i = left; i < right; i++) collector.add(matrix[top][i]);
            for(int i = top; i < bottom; i++) collector.add(matrix[i][right]);
            for(int i = right; i > left; i--) collector.add(matrix[bottom][i]);
            for(int i = bottom; i > top; i--) collector.add(matrix[i][left]);
        }
    }
}