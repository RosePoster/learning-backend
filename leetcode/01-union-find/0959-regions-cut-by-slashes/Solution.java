class Solution {
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        UnionFind uf = new UnionFind(n * n * 2);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) visit(i, j, grid, uf);
        }

        return uf.getComponents();
    }

    private void visit(int i, int j, String[] grid, UnionFind uf) {
        int n = grid.length;
        int offset = n * n;
        int leftPart = i * n + j;
        int rightPart = leftPart + offset;
        boolean isSlash = (grid[i].charAt(j) == '/');
        boolean isSpace = (grid[i].charAt(j) == ' ');

        if(j < n - 1) {
            int rightIndex = i * n + j + 1;
            uf.union(rightIndex, rightPart);
        }
        if(i < n - 1) {
            char downC = grid[i + 1].charAt(j);
            int downIndex = (i + 1) * n + j + (downC == '/' ? 0 : offset);
            uf.union(
                downIndex,
                isSlash ? rightPart : leftPart
            );
        }

        if(isSpace) uf.union(leftPart, rightPart);
    }
}

class UnionFind {
    private int[] parent;
    private int[] size;
    private int components;
    
    UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        Arrays.fill(size, 1);
        components = n;
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