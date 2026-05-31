class Solution {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        UnionFind uf = new UnionFind(n * n);
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int id = i * n + j;
                if(grid[i][j] == 0) {
                    uf.setSize(id, 0);
                    continue;
                }
                if(i - 1 >= 0 && grid[i - 1][j] == 1) uf.union(id, id - n);
                if(j - 1 >= 0 && grid[i][j - 1] == 1) uf.union(id, id - 1);
            }
        }

        int ans = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 0) {
                    int id = i * n + j;
                    int areaSize = 1;
                    Set<Integer> roots = new HashSet<>();
                    for(int[] dir : dirs) {
                        int nI = i + dir[0];
                        int nJ = j + dir[1];
                        if(nI < 0 || nI >= n || nJ < 0 || nJ >= n) continue;
                        int nId = nI * n + nJ;
                        roots.add(uf.find(nId));
                    }
                    for(int nId : roots) areaSize += uf.getSize(nId);
                    ans = Math.max(ans, areaSize);
                }                
            }
        }

        return ans == 0 ? n * n : ans;
    }

}

class UnionFind {
    private int[] parent;
    private int[] size;

    UnionFind(int n) {
        parent = new int[n];
        size = new int[n];

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
        return true;
    }

    public int getSize(int i) {
        return size[find(i)];
    }

    public void setSize(int i, int size) {
        this.size[i] = size;
    }
}