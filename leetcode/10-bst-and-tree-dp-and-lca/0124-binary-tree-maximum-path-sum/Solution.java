class Solution {
    private int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        serch(root);
        return ans;
    }

    private int serch(TreeNode curr) {
        if(curr == null) return 0;
        int left = Math.max(0, serch(curr.left));
        int right = Math.max(0, serch(curr.right));
        int val = curr.val;
        ans = Math.max(ans, val + left + right);
        return Math.max(val + left, val + right); 
    }
}