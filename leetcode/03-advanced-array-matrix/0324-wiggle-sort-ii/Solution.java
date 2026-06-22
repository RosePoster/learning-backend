class Solution {
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        quickSelect(nums);
        
        for(int i = 0; i < n)
    }

    private void quickSelect(int[] nums) {
        int target = nums.length / 2;
        int begin = 0;
        int end = nums.length - 1;

        while (begin <= end) {
            int[] range = partition(nums, begin, end);
            int lt = range[0];
            int gt = range[1];

            if (target < lt) {
                end = lt - 1;
            } else if (target > gt) {
                begin = gt + 1;
            } else {
                return;
            }
        }
    }

    private int[] partition(int[] nums, int begin, int end) {
        int pivot = nums[begin];

        int lt = begin;      // [begin, lt) < pivot
        int i = begin;       // [lt, i) == pivot
        int gt = end;        // (gt, end] > pivot

        while (i <= gt) {
            if (nums[i] < pivot) {
                swap(nums, lt, i);
                lt++;
                i++;
            } else if (nums[i] > pivot) {
                swap(nums, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}