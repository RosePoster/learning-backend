class Solution {
    record Edge(String to, double dist) {} //把值视为距离，a to b 距离为 b to a 距离倒数，a to c 距离为 a to b 和 b to c距离之积
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Edge>> graph = new HashMap<>();
        // 建图
        for(int index = 0; index < equations.size(); index++) {
            List<String> equation = equations.get(index);
            String start = equation.get(0);
            String end = equation.get(1);
            double value = values[index];

            graph.computeIfAbsent(start,k -> new ArrayList<>()).add(new Edge(end, value));
            graph.computeIfAbsent(end,k -> new ArrayList<>()).add(new Edge(start, 1 / value));
        }

        double[] ans = new double[queries.size()];
        for(int index = 0; index < queries.size(); index++) {
            Set<String> visited = new HashSet<>();
            List<String> query = queries.get(index);
            String start = query.get(0);
            String end = query.get(1);
            if(!graph.containsKey(start) || !graph.containsKey(end)) ans[index] = -1.0; // 若start和end任一不存在，答案为-1。
            else ans[index] = count(graph, start, end, 1, visited);// 否则DFS搜索答案。
        }

        return ans;
    }

    private double count(Map<String, List<Edge>> graph, String curr, String end, double dist, Set<String> visited) {
        if(curr.equals(end)) return dist; // 找到答案
        visited.add(curr); // 标记，不需要回退标记，因为只需要任意一条路径即可。
        double ans = -1;
        for(Edge e : graph.get(curr)) {
            if(visited.contains(e.to())) continue;
            ans = count(graph, e.to(), end, dist * e.dist(), visited);
            if(ans != -1) break; // 当找到答案，退出遍历
        }
        return ans;
    }
}