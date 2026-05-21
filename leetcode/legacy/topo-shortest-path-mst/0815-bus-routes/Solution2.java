class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target) return 0;
        int n = routes.length;
        Map<Integer, List<Integer>> nodeToRoutes = new HashMap<>();
        Set<Integer>[] graph = new Set[n];
        for(int i = 0; i < n; i++) {
            graph[i] = new HashSet<>();
            int[] route = routes[i];
            for(int node : routes[i]) {
                List<Integer> nodeToRoute = nodeToRoutes.computeIfAbsent(node, k -> new ArrayList<>());
                nodeToRoute.add(i);
            }
        }

        for(int node : nodeToRoutes.keySet()) {
            List<Integer> nodeToRoute = nodeToRoutes.get(node);
            for(int route : nodeToRoute) {
                for(int nextRoute : nodeToRoute) {
                    if(route != nextRoute) graph[route].add(nextRoute);
                }
            }
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> targets = new HashSet<>();
        int ans = 1;

        for(int node : nodeToRoutes.keySet()) {
            List<Integer> nodeToRoute = nodeToRoutes.get(node);
            if(node == source) {
                for(int i : nodeToRoute) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            if(node == target) {
                for(int i : nodeToRoute) {
                    targets.add(i);
                }
            }
        }

        for(int i : targets) if(visited[i]) return ans;
        while(!queue.isEmpty()) {
            int size = queue.size();
            ans++;
            for(int Index = 0; Index < size; Index++) {
                int curr = queue.poll();
                for(int next : graph[curr]) {
                    if(visited[next]) continue;
                    if(targets.contains(next)) return ans;
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }

        return -1;
    }
}
