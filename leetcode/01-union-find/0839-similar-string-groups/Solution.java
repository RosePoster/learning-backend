class Solution {
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        UnionFind uf = new UnionFind(n);

        for(int i = 1; i < n; i++) {
            String curr = strs[i];
            for(int j = 0; j < i; j++) {
                String pre = strs[j];
                if(uf.find(i) == uf.find(j)) continue;
                if(isSimilar(curr, pre)) uf.union(i, j);
            }
        }

        return uf.getComponents();
    }

    private boolean isSimilar(String str1, String str2) {
        int diffs = 0;
        for(int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i)) diffs++;
            if(diffs > 2) return false;
        }

        return true;
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