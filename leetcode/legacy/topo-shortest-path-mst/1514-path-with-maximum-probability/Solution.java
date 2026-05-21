class Solution {
    record State(int node, double prob) {}
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<State>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for(int e = 0; e < edges.length; e++) {
            int i = edges[e][0];
            int j = edges[e][1];
            double w = succProb[e];

            graph[i].add(new State(j, w));
            graph[j].add(new State(i, w));
        }

        double[] probs = new double[n];
        boolean[] visited = new boolean[n];

        PriorityQueue<State> pq = new PriorityQueue<>(
            Comparator.comparingDouble((State s) -> s.prob).reversed()
        );
        pq.offer(new State(start_node, 1));

        while(!visited[end_node] && !pq.isEmpty()) {
            State curr = pq.poll();
            int currNode = curr.node;
            if(visited[currNode]) continue;
            visited[currNode] = true;
            double currProb = curr.prob;

            for(State nextEdge : graph[currNode]) {
                int next = nextEdge.node;
                double nextProb = nextEdge.prob * currProb;
                if(nextProb <= probs[next]) continue;
                probs[next] = nextProb;
                pq.offer(new State(next, nextProb));
            }
        }

        return probs[end_node];
    }
}