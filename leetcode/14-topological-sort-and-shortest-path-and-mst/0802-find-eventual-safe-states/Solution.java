class Solution {
    private boolean[] isSafe;
    private boolean[] visited;

    public List<Integer> eventualSafeNodes(int[][] graph) {
        int len = graph.length;
        isSafe = new boolean[len];
        visited = new boolean[len];

        for(int i = 0; i < len; i++) {
            if(visited[i]) continue;
            visit(graph, i);
        }

        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            if(isSafe[i]) ans.add(i);
        }

        return ans;
    }

    private void visit(int[][] graph, int curr) {
        visited[curr] = true;
        if(graph[curr] == null || graph[curr].length == 0) {
            isSafe[curr] = true;
            return;
        }

        for(int next : graph[curr]) {
            if(!visited[next]) visit(graph, next);
            if(isSafe[next]) continue;
            return;
        }
        isSafe[curr] = true;
    }
}