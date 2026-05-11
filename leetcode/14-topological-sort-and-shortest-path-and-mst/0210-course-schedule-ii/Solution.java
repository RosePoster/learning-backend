class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        int[] inDegree = new int[numCourses];
        for(int[] e : prerequisites) {
            graph[e[1]].add(e[0]);
            inDegree[e[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] ans = new int[numCourses];
        int visitedCount = 0;
        for(int v = 0; v < numCourses; v++) {
            if(inDegree[v] == 0) {
                queue.offer(v);
                ans[visitedCount++] = v;
            }
        }

        while(!queue.isEmpty()) {
            List<Integer> curr = graph[queue.poll()];
            for(int v : curr) {
                inDegree[v]--;
                if(inDegree[v] == 0) {
                    queue.offer(v);
                    ans[visitedCount++] = v;
                }
            }            
        }

        return visitedCount == numCourses ? ans : new int[0];
    }
}