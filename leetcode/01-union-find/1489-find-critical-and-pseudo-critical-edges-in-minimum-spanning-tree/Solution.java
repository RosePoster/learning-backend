class Solution {
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        int m = edges.length;
        UnionFind uf = new UnionFind(n);
        int[][] newEdges = new int[m][4];
        for (int k = 0; k < m; k++) {
            newEdges[k][0] = edges[k][0];
            newEdges[k][1] = edges[k][1];
            newEdges[k][2] = edges[k][2];
            newEdges[k][3] = k;
        }

        Arrays.sort(newEdges, (a, b) -> Integer.compare(a[2], b[2]));
        List<List<Integer>> ans = new ArrayList<>();
        List<int[]> currEdges = new ArrayList<>();
        ans.add(new ArrayList<>());
        ans.add(new ArrayList<>());

        int i = 0;
        while(i < m && uf.getComponents() > 1) {
            int weight = newEdges[i][2];
            while(i < m) {
                int[] edge = newEdges[i];
                int index = edge[3];
                if(edge[2] != weight) break;
                int rootI = uf.find(edge[0]);
                int rootJ = uf.find(edge[1]);
                if(rootI != rootJ) currEdges.add(new int[]{rootI, rootJ, index});
                i++;
            }

            markBridges(currEdges, ans.get(0), ans.get(1), n, m);
            for(int[] e : currEdges) uf.union(e[0], e[1]);
            currEdges.clear();
        }

        return ans;
    }

    private void markBridges(List<int[]> edges, List<Integer> criticalEdges, List<Integer> notCriticalEdges, int n, int m) {
        List<int[]>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for(int[] e : edges) {
            int i = e[0];
            int j = e[1];
            int index = e[2];
            graph[i].add(new int[]{j, index});
            graph[j].add(new int[]{i, index});
        }
        int[] low = new int[n];
        int[] dth = new int[n];

        boolean[] visited = new boolean[n];
        boolean[] isBridge = new boolean[m];
        for(int i = 0; i < n; i++) {
            if(visited[i]) continue;
            dfs(i, graph, visited, isBridge, dth, low, 0, -1);
        }

        for (int[] e : edges) {
            int edgeId = e[2];
            if (isBridge[edgeId]) {
                criticalEdges.add(edgeId);
            } else {
                notCriticalEdges.add(edgeId);
            }
        }
    }

    private void dfs(
        int curr, 
        List<int[]>[] graph, 
        boolean[] visited, 
        boolean[] isBridge,
        int[] dth, 
        int[] low, 
        int currDth, 
        int parentEdgeId
    ) {
        visited[curr] = true;
        dth[curr] = low[curr] = currDth;

        for (int[] e : graph[curr]) {
            int next = e[0];
            int edgeId = e[1];

            if (edgeId == parentEdgeId) continue;

            if (!visited[next]) {
                dfs(next, graph, visited, isBridge, dth, low, currDth + 1, edgeId);
                low[curr] = Math.min(low[curr], low[next]);
                if (low[next] > dth[curr]) isBridge[edgeId] = true;
            } else {
                low[curr] = Math.min(low[curr], dth[next]);
            }
        }
    }
}

class UnionFind {
    private int[] parent;
    private int[] size;
    private int components;

    UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        components = n;
        for(int i = 0; i < n; i++) parent[i] = i;
        Arrays.fill(size, 1);
    }

    public int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if(rootI == rootJ) return false;

        if(size[rootI] < size[rootJ]) {
            int temp = rootI;
            rootI = rootJ;
            rootJ = temp;
        }
        parent[rootJ] = rootI;
        size[rootI] += size[rootJ];
        components--;

        return true;
    }

    public int getComponents() {
        return components;
    }
}