class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        List<int[]>[] limGraph = new ArrayList[n];
        for (int i = 0; i < n; i++) limGraph[i] = new ArrayList<>();

        for (int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            int w = edge[2];

            if (w > distanceThreshold) continue;

            limGraph[i].add(new int[]{j, w});
            limGraph[j].add(new int[]{i, w});
        }

        int minCount = Integer.MAX_VALUE;
        int ans = -1;

        for (int i = 0; i < n; i++) {
            int currCount = visit(i, distanceThreshold, limGraph, minCount);

            if (currCount > minCount) continue;

            minCount = currCount;
            ans = i;
        }

        return ans;
    }

    private int visit(
        int curr,
        int distanceThreshold,
        List<int[]>[] limGraph,
        int minCount
    ) {
        int n = limGraph.length;

        int[] dist = new int[n];
        Arrays.fill(dist, distanceThreshold + 1);
        dist[curr] = 0;

        boolean[] visited = new boolean[n];

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );
        pq.offer(new int[]{curr, 0});

        int currCount = 0;

        while (currCount <= minCount && !pq.isEmpty()) {
            int[] node = pq.poll();
            int i = node[0];
            int d = node[1];

            if (visited[i]) continue;
            visited[i] = true;

            // i < curr：limGraph[i] 已经不是原始边，而是 i 的阈值内最短路缓存。
            // 直接批量更新 dist，不把新状态放入 pq。
            if (i < curr) {
                for (int[] cached : limGraph[i]) {
                    int j = cached[0];
                    int w = d + cached[1];

                    if (w > distanceThreshold || w >= dist[j]) continue;

                    if (dist[j] == distanceThreshold + 1) currCount++;
                    dist[j] = w;
                }
                continue;
            }

            // i >= curr：limGraph[i] 仍是原始邻接边，走普通 Dijkstra 松弛。
            for (int[] next : limGraph[i]) {
                int j = next[0];
                int w = d + next[1];

                if (visited[j] || w >= dist[j]) continue;

                if (dist[j] == distanceThreshold + 1) currCount++;
                dist[j] = w;
                pq.offer(new int[]{j, w});
            }
        }

        // 将 curr 的 dist 结果翻译为缓存：
        // limGraph[curr] 从原始邻接边替换为 “curr 阈值内可达城市 + 最短距离”。
        List<int[]> cachedReachable = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == curr) continue;
            if (dist[i] <= distanceThreshold) {
                cachedReachable.add(new int[]{i, dist[i]});
            }
        }
        limGraph[curr] = cachedReachable;

        return currCount;
    }
}
/**
 * Experimental version.
 * Demonstrates shortcut-cache reuse, but is not strictly correct because
 * discovered distances may be cached as shortest-path shortcuts before finalized.
 */