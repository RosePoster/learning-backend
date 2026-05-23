class Solution {
    private class UnionFind {
        private int[] parent = new int[26];
        
        UnionFind() {
            for(int i = 0; i < 26; i++) parent[i] = i;
        }

        int find(int n) {
            if(parent[n] != n) parent[n] = find(parent[n]);
            return parent[n];
        }

        void unit(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if(rootI != rootJ) {
                parent[rootJ] = rootI;
            }
        }
    }

    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind();
        for(String s : equations) {
            if(s.charAt(1) == '!')  continue;
            int i = s.charAt(0) - 'a';
            int j = s.charAt(3) - 'a';
            uf.unit(i, j);
        }

        for(String s : equations) {
            if(s.charAt(1) == '=')  continue;
            int i = s.charAt(0) - 'a';
            int j = s.charAt(3) - 'a';
            if(uf.find(i) == uf.find(j)) return false;
        }

        return true;
    }
}