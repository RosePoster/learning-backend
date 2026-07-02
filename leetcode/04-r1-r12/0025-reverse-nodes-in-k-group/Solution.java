class Solution {
    private int ans = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        search(root);
        return ans;
    }

    private int search(TreeNode root) {
        if (root == null) return 0;
        int left = Math.max(search(root.left), 0);
        int right = Math.max(search(root.right), 0);
        ans = Math.max(root.val + left + right, ans);
        return Math.max(left, right) + root.val;
    }
}