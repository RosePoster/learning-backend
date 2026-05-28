class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length + 1;
        UnionFind uf = new UnionFind(n);

        List<int[]> candidates = new ArrayList<>();
        boolean[] hasFrom = new boolean[n];
        int doubleFromNode = -1;
        for(int[] edge : edges) {
            if(hasFrom[edge[1]]) doubleFromNode = edge[1];
            hasFrom[edge[1]] = true;
        }

        for(int[] edge : edges) {
            if(edge[1] == doubleFromNode) {
                candidates.add(edge);
                continue;
            }
            if(!uf.union(edge)) return edge;
        }

        
        return uf.union(candidates.get(0)) ? candidates.get(1) : candidates.get(0);        
    }
}

class UnionFind {
    private int[] parent;

    UnionFind (int n) {
        parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public boolean union(int[] edge) {
        int rootFrom = find(edge[0]);
        int rootTo = find(edge[1]);

        if(rootFrom == rootTo) {
            return false;
        }
        parent[rootTo] = rootFrom;
        return true;
    }
}
