class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0l, 1);
        return search(root, targetSum, 0l, map);
    }

    int search(TreeNode curr, int targetSum, long preSum, Map<Long, Integer> map) {
        if (curr == null) return 0;
        long currSum = preSum + curr.val;
        int currPaths = map.getOrDefault(currSum - targetSum, 0);
        map.merge(currSum, 1, Integer::sum);
        currPaths += search(curr.left, targetSum, currSum, map) + search(curr.right, targetSum, currSum, map);
        map.merge(currSum, -1, Integer::sum);
        return currPaths;
    }
}