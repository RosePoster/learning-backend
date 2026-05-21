class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target) return 0;
        int n = routes.length;
        Set<Integer>[] setRoutes = new HashSet[n];
        Set<Integer>[] graph = new Set[n];
        for(int i = 0; i < n; i++) {
            setRoutes[i] = new HashSet<>();
            graph[i] = new HashSet<>();
            for(int node : routes[i]) {
                setRoutes[i].add(node);
                for(int j = 0; j < i; j++) {
                    if(setRoutes[j].contains(node)) {
                        graph[j].add(i);
                        graph[i].add(j);
                    }
                }
            }
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> targets = new HashSet<>();
        int ans = 1;

        for(int i = 0; i < n; i++) {
            Set<Integer> setRoute = setRoutes[i];
            if(setRoute.contains(source)) {
                queue.offer(i);
                visited[i] = true;
            }
            if(setRoute.contains(target)) targets.add(i);
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