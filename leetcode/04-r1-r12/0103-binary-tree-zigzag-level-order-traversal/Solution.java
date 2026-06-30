class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> deque1 = new ArrayDeque<>();
        Deque<TreeNode> deque2 = new ArrayDeque<>();
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        deque1.offerFirst(root);
        boolean isForword = true;
        while (!deque1.isEmpty()) {
            List<Integer> currL = new ArrayList<>();
            while (!deque1.isEmpty()) {
                TreeNode curr = deque1.pollFirst();
                if (isForword) {
                    if (curr.left != null) deque2.offerFirst(curr.left);
                    if (curr.right != null) deque2.offerFirst(curr.right);
                } else {
                    if (curr.right != null) deque2.offerFirst(curr.right);
                    if (curr.left != null) deque2.offerFirst(curr.left);
                }
                currL.add(curr.val);
            }
            isForword = !isForword;
            ans.add(currL);
            Deque<TreeNode> temp = deque1;
            deque1 = deque2;
            deque2 = temp;
        }
        return ans;
    }
}