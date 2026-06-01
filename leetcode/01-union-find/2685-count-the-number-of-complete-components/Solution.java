class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        for(int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        Set<Integer> roots = new HashSet<>();
        for(int i = 0; i < n; i++) roots.add(uf.find(i));

        int completeComponents = 0;
        for(int root : roots) {
            if(uf.isCompleteComponent(root)) completeComponents++;
        }

        return completeComponents;
    }
}

class UnionFind {

    private int[] parent;
    private int[] size;
    private int[] componentsEdges;


    UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        componentsEdges = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        Arrays.fill(size, 1);
        Arrays.fill(componentsEdges, 0);
    }

    public int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if(rootI == rootJ) {
            componentsEdges[rootI]++;
            return false;
        }

        if(size[rootI] < size[rootJ]) {
            int temp = rootI;
            rootI = rootJ;
            rootJ = temp;
        }
        parent[rootJ] = rootI;
        size[rootI] += size[rootJ];
        componentsEdges[rootI] += componentsEdges[rootJ] + 1;
        return true;
    }

    public boolean isCompleteComponent(int root) {
        int n = size[root];
        int edges = componentsEdges[root];

        return edges == n * (n - 1) / 2; 
    }
}