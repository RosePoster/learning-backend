class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        int[] nextGreater1 = buildNextGreater(nums1);
        int[] nextGreater2 = buildNextGreater(nums2);
        int[] ans = new int[k];
        int skip = m + n - k;
        int idx1 = 0;
        int idx2 = 0;

        for (int i = 0; i < k; i++) {
            int limit1 = skip - idx2 + i;
            int limit2 = skip - idx1 + i;
            int nextIdx1 = getNextIdx(nextGreater1, idx1, limit1);
            int nextIdx2 = getNextIdx(nextGreater2, idx2, limit2);
            int curr = 0;
            int num1 = nextIdx1 == n ? -1 : nums1[nextIdx1];
            int num2 = nextIdx2 == m ? -1 : nums2[nextIdx2];
            if (num1 > num2) {
                idx1 = nextIdx1;
                curr = nums1[idx1++];
            } else if (num2 > num1) {
                idx2 = nextIdx2;
                curr = nums2[idx2++];
            } else {
                // 这里需要处理相等，由于存在可跳过预算，所以状态空间过大。
            }
            ans[i] = curr;
        }

        return ans;
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

    private int getNextIdx(int[] nextGreater, int idx, int limit) {
        int len = nextGreater.length;
        int nextIdx = idx;
        while (nextIdx < len && nextGreater[nextIdx] < len && nextGreater[nextIdx] <= limit) {
            nextIdx = nextGreater[nextIdx];
        }
        return nextIdx;
    }
}

// 这一版没有处理相等，所以是错误的。