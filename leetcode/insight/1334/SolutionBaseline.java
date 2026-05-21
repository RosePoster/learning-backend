class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        List<int[]>[] limGraph = new ArrayList[n];
        for(int i = 0; i < n; i++) limGraph[i] = new ArrayList<>();
        for(int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            int w = edge[2];
            if(w > distanceThreshold) continue;
            limGraph[i].add(new int[]{j, w});
            limGraph[j].add(new int[]{i, w});
        }

        int minCount = Integer.MAX_VALUE;
        int ans = -1;
        for(int i = 0; i < n; i++) {
            int currCount = visit(i, distanceThreshold, limGraph, minCount);
            if(currCount > minCount) continue;
            minCount = currCount;
            ans = i;
        }

        return ans;
    }

    private int visit(int curr, int distanceThreshold, List<int[]>[] limGraph, int minCount) {
        int n = limGraph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, distanceThreshold + 1);
        int currCount = 0;
        dist[curr] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );
        boolean[] visited = new boolean[limGraph.length];
        pq.offer(new int[]{curr, 0});

        while(currCount <= minCount && !pq.isEmpty()) {
            int[] node = pq.poll();
            int i = node[0];
            int d = node[1];
            if(visited[i]) continue;
            visited[i] = true;
            for(int[] next : limGraph[i]) {
                int j = next[0];
                int w = d + next[1];
                if(visited[j] || w >= dist[j]) continue;
                if(dist[j] == distanceThreshold + 1)  currCount++;
                dist[j] = w;
                pq.offer(new int[]{j ,dist[j]});
            }
        }

        return currCount;
    }
}
