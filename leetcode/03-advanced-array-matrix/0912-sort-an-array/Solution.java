class Solution {
    private final Random random = new Random();

    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;    
    }

    private void quickSort(int[] nums, int begin, int end) {
        if(begin >= end) return;

        int pivotIndex = begin + random.nextInt(end - begin + 1);
        swap(nums, begin, pivotIndex);

        int[] range = partition(nums, begin, end);
        int lt = range[0];
        int gt = range[1];

        quickSort(nums, begin, lt - 1);
        quickSort(nums, gt + 1, end);
    }

    private int[] partition(int[] nums, int begin, int end) {
        int pivot = nums[begin];

        int lt = begin;
        int i = begin;
        int gt = end;

        while(i <= gt) {
            if(nums[i] < pivot) {
                swap(nums, i, lt);
                lt++;
                i++;
            } else if(nums[i] > pivot) {
                swap(nums, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    private void swap(int[] nums, int i, int j) {
        if(i == j) return;
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}