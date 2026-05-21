class Solution {
    private List<Integer> path = new ArrayList<>();
    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        dfs(graph, 0);
        return ans;
    }

    private void dfs(int[][] graph, int curr) {
        path.add(curr);
        if(curr == graph.length - 1) {
            ans.add(new ArrayList<Integer>(path));
        } else {
            for(int next : graph[curr]) {
                dfs(graph, next);
            }
        }
        path.remove(path.size() - 1);
    }
}