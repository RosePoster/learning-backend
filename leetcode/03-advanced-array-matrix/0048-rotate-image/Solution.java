class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n / 2; i++) {
            rotateL(matrix, i);
        }
    }

    private void rotateL(int[][] matrix, int l) {
        int n = matrix.length;
        for(int i = l; i < n - l - 1; i++) {
            swap(matrix, 
                 l, i,
                 i, n - l - 1,
                 n - l - 1, n - i - 1,
                 n - i - 1, l);
        }
    }

    private void swap(int[][] matrix, 
                      int i1, int j1,
                      int i2, int j2,
                      int i3, int j3,
                      int i4, int j4) 
    {
        int temp = matrix[i2][j2];
        matrix[i2][j2] = matrix[i1][j1];
        matrix[i1][j1] = matrix[i4][j4];
        matrix[i4][j4] = matrix[i3][j3];
        matrix[i3][j3] = temp;
    }
}
