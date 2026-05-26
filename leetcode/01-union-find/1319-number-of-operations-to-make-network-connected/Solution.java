class Solution {
    class UnionFind {
        private int[] parent;
        private int components;

        UnionFind(int n) {
            parent = new int[n];
            for(int i = 0; i < n; i++) parent[i] = i;
            components = n;
        }

        public int find(int i) {
            if(parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            
            if(rootI == rootJ) return false;
            parent[rootJ] = rootI;
            components--;

            return true;
        }

        public int getComponents() {
            return components;
        }
    }

    public int makeConnected(int n, int[][] connections) {
        if(connections.length < n - 1) return -1;

        UnionFind uf = new UnionFind(n);
        for(int[] connection : connections) {
            int i = connection[0];
            int j = connection[1];
            uf.union(i, j);
        }

        return uf.getComponents() - 1;
    }
}