class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        UnionFind uf = new UnionFind(n);
        for(List<Integer> pair : pairs) {
            int i = pair.get(0);
            int j = pair.get(1);
            uf.union(i, j);
        }
        
        Map<Integer, PriorityQueue<Character>> root2Pq = new HashMap<>();
        PriorityQueue<Character>[] node2Pq = new PriorityQueue[n];

        for(int i = 0; i < n; i++) {
            int root = uf.find(i);
            PriorityQueue<Character> pq = root2Pq.computeIfAbsent(
                root, 
                k -> new PriorityQueue<>()
            );
            pq.offer(s.charAt(i));
            node2Pq[i] = pq;
        }

        StringBuilder sb = new StringBuilder();
        for(PriorityQueue<Character> pq : node2Pq) sb.append(pq.poll());

        return sb.toString();
    }
}

class UnionFind {
    private int[] parent;
    private int[] size;
    private int components;

    UnionFind(int n) {
        parent = new int[n];
        for(int i = 0; i < n; i++ ) parent[i] = i;
        
        size = new int[n];
        Arrays.fill(size, 1);

        components = n;
    }

    public int getComponents() {
        return components;
    }

    public int find(int i) {
        return parent[i] == i ? i : (parent[i] = find(parent[i]));
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        
        if(rootI == rootJ) return false;
        if(size[rootI] < size[rootJ]) {
            int temp = rootJ;
            rootJ = rootI;
            rootI = temp;
        }

        parent[rootJ] = rootI;
        size[rootI] += size[rootJ];
        components--;
        
        return true;
    }
}