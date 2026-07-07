class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        int[] nextGreater1 = buildNextGreater(nums1);
        int[] nextGreater2 = buildNextGreater(nums2);
        int[] ans = new int[k];
        for (int i = Math.max(k - m, 0); i <= k && i <= n; i++) {
            int[] subNums1 = buildSubNums(nums1, nextGreater1, i);
            int[] subNums2 = buildSubNums(nums2, nextGreater2, k - i);
            ans = compare(ans, merge(subNums1, subNums2));
        }
        return ans;
    }

    private int[] compare(int[] nums1, int[] nums2) {
        if (nums1.length != nums2.length) return null;
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] > nums2[i]) return nums1;
            else if (nums1[i] < nums2[i]) return nums2;
        }
        return nums1;
    }

    private int[] merge(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int[] ans = new int[n + m];
        int idx = 0;
        int i = 0;
        int j = 0;
        while (idx < n + m) {
            if (greater(nums1, i, nums2, j)) ans[idx++] = nums1[i++];
            else ans[idx++] = nums2[j++];
        }
        return ans;
    }
    
    private boolean greater(int[] a, int i, int[] b, int j) {
        while (i < a.length && j < b.length && a[i] == b[j]) {
            i++;
            j++;
        }
        return j == b.length || (i < a.length && a[i] > b[j]);
    }

    private int[] buildSubNums(int[] nums, int[] nextGreater, int size) {
        int[] subNums = new int[size];
        int n = nums.length;
        int idx = 0;
        for (int i = 0; i < size; i++) {
            while (n - nextGreater[idx] >= size - i) idx = nextGreater[idx];
            subNums[i] = nums[idx++];
        }
        return subNums;
    }


    private int[] buildNextGreater(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = nums.length;
        int[] nextGreater = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                stack.pop();
            }
            nextGreater[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        return nextGreater;
    }
}