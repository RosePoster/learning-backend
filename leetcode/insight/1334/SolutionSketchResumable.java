class Solution {
    private List<int[]>[] graph;
    private DijkstraState[] states;
    private int distanceThreshold;

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        this.distanceThreshold = distanceThreshold;
        this.graph = new ArrayList[n];
        this.states = new DijkstraState[n];

        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            int w = edge[2];

            if (w > distanceThreshold) continue;

            graph[i].add(new int[]{j, w});
            graph[j].add(new int[]{i, w});
        }

        int minCount = Integer.MAX_VALUE;
        int ans = -1;

        for (int i = 0; i < n; i++) {
            DijkstraState state = getState(i);
            resume(state, distanceThreshold, minCount);

            int currCount = state.reachableCount;
            if (currCount > minCount) continue;

            minCount = currCount;
            ans = i;
        }

        return ans;
    }

    private DijkstraState getState(int source) {
        if (states[source] == null) {
            states[source] = new DijkstraState(source, graph.length, distanceThreshold);
        }

        return states[source];
    }

    private void resume(DijkstraState state, int budget, int stopAfterCount) {
        if (budget < 0) return;

        while (state.reachableCount <= stopAfterCount) {
            if (!resumeNext(state, budget, stopAfterCount)) return;
        }
    }

    private boolean resumeNext(DijkstraState state, int budget, int stopAfterCount) {
        while (!state.pq.isEmpty()) {
            int[] node = state.pq.peek();
            int i = node[0];
            int d = node[1];

            if (d > budget) return false;

            state.pq.poll();

            if (state.finalized[i] || d != state.dist[i]) continue;

            state.finalized[i] = true;
            state.finalizedNodes.add(i);

            expand(state, i, d, stopAfterCount);

            return true;
        }

        return false;
    }

    private void expand(DijkstraState state, int i, int d, int stopAfterCount) {
        if (i < state.source) {
            expandFromCache(state, i, d, stopAfterCount);
            return;
        }

        for (int[] next : graph[i]) {
            int j = next[0];
            int w = d + next[1];

            relax(state, j, w);
        }
    }

    private void expandFromCache(
        DijkstraState state,
        int cachedSource,
        int d,
        int stopAfterCount
    ) {
        int remainingBudget = distanceThreshold - d;
        DijkstraState cachedState = getState(cachedSource);

        int appliedCount = 0;
        while (state.reachableCount <= stopAfterCount) {
            appliedCount = applyCachedFinalized(state, cachedState, d, remainingBudget, appliedCount);
            if (state.reachableCount > stopAfterCount) return;
            if (!resumeNext(cachedState, remainingBudget, Integer.MAX_VALUE)) return;
        }
    }

    private int applyCachedFinalized(
        DijkstraState state,
        DijkstraState cachedState,
        int d,
        int remainingBudget,
        int start
    ) {
        int i = start;
        while (i < cachedState.finalizedNodes.size()) {
            int j = cachedState.finalizedNodes.get(i);
            if (cachedState.dist[j] <= remainingBudget) {
                relax(state, j, d + cachedState.dist[j]);
            }
            i++;
        }

        return i;
    }

    private void relax(DijkstraState state, int j, int w) {
        if (w > distanceThreshold) return;
        if (state.finalized[j] || w >= state.dist[j]) return;

        if (!state.reached[j]) {
            state.reached[j] = true;
            if (j != state.source) state.reachableCount++;
        }

        state.dist[j] = w;
        state.pq.offer(new int[]{j, w});
    }

    private static class DijkstraState {
        int source;
        int[] dist;
        boolean[] reached;
        boolean[] finalized;
        List<Integer> finalizedNodes;
        PriorityQueue<int[]> pq;
        int reachableCount;

        DijkstraState(int source, int n, int distanceThreshold) {
            this.source = source;
            this.dist = new int[n];
            this.reached = new boolean[n];
            this.finalized = new boolean[n];
            this.finalizedNodes = new ArrayList<>();
            this.pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

            Arrays.fill(dist, distanceThreshold + 1);
            dist[source] = 0;
            reached[source] = true;
            pq.offer(new int[]{source, 0});
        }
    }
}
