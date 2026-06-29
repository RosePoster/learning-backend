class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int maxVal = Math.max(p.val, q.val);
        int minVal = Math.min(p.val, q.val);
        TreeNode curr = root;
        while(curr != null && (curr.val < minVal || curr.val > maxVal)) {
            if (curr.val < minVal) curr = curr.right;
            else curr = curr.left;
        }
        return curr;
    }
}