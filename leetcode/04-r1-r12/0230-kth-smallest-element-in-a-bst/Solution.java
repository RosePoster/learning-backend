class Solution {
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr = root;
        int ans = -1;
        int visitSize = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            visitSize++;
            if (visitSize == k) {
                ans = curr.val;
                return ans;
            }
            curr = curr.right;
        }
        return ans;
    }
}

/**
// 递归版本

class Solution {
    private int ans = -1;
    public int kthSmallest(TreeNode root, int k) {
        int[] visitedSize = new int[1];
        search(root, k, visitedSize);
        return ans;
    }

    private void search(TreeNode curr, int k, int[] visitedSize) {
        if (curr == null || ans != -1) return;
        search(curr.left, k, visitedSize);
        visitedSize[0]++;
        if (visitedSize[0] == k) ans = curr.val;
        else search(curr.right, k, visitedSize);
    }
}

*/