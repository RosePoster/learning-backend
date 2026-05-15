class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        int[] inDegree = new int[n];
        List<Integer>[] nextEdges = new ArrayList[n];
        for(int i = 0; i < n; i++) nextEdges[i] = new ArrayList<>();
        for(int[] edge : edges) {
            nextEdges[edge[0]].add(edge[1]);
            nextEdges[edge[1]].add(edge[0]);
            inDegree[edge[0]]++;
            inDegree[edge[1]]++;
        }

        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(inDegree[i] > 1) continue;
            ans.add(i);
        }

        while(true) {
            List<Integer> nextList = new ArrayList<>();
            for(int i : ans) {
                for(int next : nextEdges[i]) {
                    inDegree[next]--;
                    if(inDegree[next] == 1) nextList.add(next);
                }
            }
            if(nextList.isEmpty()) break;
            ans = nextList;
        }

        return ans;
    }
}