class Solution1 {
    private final Random random = new Random();

    public int[][] kClosest(int[][] points, int k) {
        sort(points, k);
        return Arrays.copyOfRange(points, 0, k);
    }

    private void sort(int[][] points, int target) {
        int begin = 0;
        int end = points.length - 1;

        while(begin < end) {
            int[] range = partition(points, begin, end);
            int lt = range[0];
            int gt = range[1];
            if(target < lt) {
                end = lt - 1;
            } else if(target > gt + 1) {
                begin = gt + 1;
            } else break;
        }
    }

    private int[] partition(int[][] points, int begin, int end) {
        int pivotIndex = begin + random.nextInt(end - begin + 1);
        swap(points, begin, pivotIndex);
        int pivot = dist(points[begin]);
        int lt = begin;
        int i = begin;
        int gt = end;
        
        while(i <= gt) {
            int curr = dist(points[i]);
            if(curr < pivot) {
                swap(points, i, lt);
                lt++;
                i++;
            } else if(curr > pivot) {
                swap(points, i, gt);
                gt--;
            } else i++;
        }

        return new int[]{lt, gt};
    }

    private int dist(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    private void swap(int[][] points, int i, int j) {
        if(i == j) return;
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
}