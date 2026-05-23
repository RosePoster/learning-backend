class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int prov = 0;

        for(int i = 0; i < n; i++) {
            if(visited[i]) continue;
            prov++;
            dfs(i, isConnected, visited);
        }

        return prov;
    }

    private void dfs(int curr, int[][] isConnected, boolean[] visited) {
        if(visited[curr]) return;
        visited[curr] = true;

        for(int i = 0; i < isConnected.length; i++) {
            if(isConnected[curr][i] == 0 || visited[i]) continue;
            dfs(i, isConnected, visited);
        }
    }
}