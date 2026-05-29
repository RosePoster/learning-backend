class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        UnionFind uf = new UnionFind(n);
        for(int[] edge : edges) uf.union(edge);
        return !uf.union(new int[]{source, destination});
    }
}

class UnionFind {
    int[] parent;
    int[] size;

    UnionFind(int n) {
        parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        size = new int[n];
        Arrays.fill(size, 1);
    }

    public int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public boolean union(int[] edge) {
        int rootI = find(edge[0]);
        int rootJ = find(edge[1]);

        if(rootI == rootJ) return false;
        if(size[rootI] < size[rootJ]) {
            int temp = rootI;
            rootI = rootJ;
            rootJ = temp;
        }

        parent[rootJ] = rootI;
        size[rootI] += size[rootJ];
        return true;
    }
}