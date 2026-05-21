class Solution {
    class UnionFind {
        int[] parent;
        int size;

        UnionFind(int n) {
            size = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) return false;

            parent[rootA] = rootB;
            size--;
            return true;
        }
    }

    class Edge {
        int a;
        int b;
        int cost;

        Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        UnionFind uf = new UnionFind(n);
        List<Edge> edges = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                int cost = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new Edge(i, j, cost));
            }
        }

        edges.sort(Comparator.comparingInt(e -> e.cost));

        int ans = 0;
        
        for(Edge edge : edges) {
            int a = edge.a;
            int b = edge.b;
            int cost = edge.cost;

            if(uf.union(a, b)) ans += cost;
            if(uf.size == 1) break;
        }

        return ans;
    }
}