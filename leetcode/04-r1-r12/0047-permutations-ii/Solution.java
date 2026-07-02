class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        backTrack(ans, path, visited, nums);
        return ans;
    }

    private void backTrack(List<List<Integer>> ans, List<Integer> path, boolean[] visited, int[] nums) {
        if (path.size() == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        Set<Integer> used = new HashSet<>();
        for(int i = 0; i < nums.length; i++) {
            if (visited[i] || used.contains(nums[i])) continue;
            used.add(nums[i]);
            visited[i] = true;
            path.add(nums[i]);
            backTrack(ans, path, visited, nums);
            visited[i] = false;
            path.remove(path.size() - 1);
        }
    }
}