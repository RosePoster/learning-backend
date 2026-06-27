class Solution {
    private TreeNode ans = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return ans;
    }

    private boolean dfs(TreeNode curr, TreeNode p, TreeNode q) {
        if (ans != null || curr == null) return false;
        boolean currHas = curr == p || curr == q;
        boolean leftHas = dfs(curr.left, p, q);
        boolean rightHas = dfs(curr.right, p, q);
        if(leftHas && rightHas || currHas && (leftHas || rightHas)) ans = curr;
        return currHas || leftHas || rightHas;
    }
}