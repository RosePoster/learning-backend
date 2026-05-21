class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target) return 0;

        int n = routes.length;
        Map<Integer, List<Integer>> nodeToRoutes = new HashMap<>();
        for(int i = 0; i < n; i++) {
            for(int node : routes[i]) {
                List<Integer> nodeToRoute = nodeToRoutes.computeIfAbsent(node, k -> new ArrayList<>());
                nodeToRoute.add(i);
            }
        }

        boolean[] visitedRoute = new boolean[n];
        Set<Integer> visitedNode = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int node : nodeToRoutes.keySet()) {
            if(node != source) continue;
            List<Integer> nodeToRoute = nodeToRoutes.get(node);
            for(int route : nodeToRoute) {
                visitedRoute[route] = true;
                queue.offer(route);
            }
            visitedNode.add(source);
            break;
        }

        int ans = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();

            for(int Index = 0; Index < size; Index++) {
                int currRoute = queue.poll();

                for(int nextNode : routes[currRoute]) {
                    if(visitedNode.contains(nextNode)) continue;
                    if(nextNode == target) return ans;

                    visitedNode.add(nextNode);
                    List<Integer> nodeToRoute = nodeToRoutes.get(nextNode);
                    for(int nextRoute : nodeToRoute) {
                        if(visitedRoute[nextRoute]) continue;
                        visitedRoute[nextRoute] = true;

                        queue.offer(nextRoute);
                    }
                }
            }

            ans++;
        }

        return -1;
    }
}
