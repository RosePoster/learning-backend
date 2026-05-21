class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        // count degree and build support gruph
        int[] taskDegree = new int[n];  // 永远表示 item x 还未完成的前置 item 数量
        int[] groupExternalDegree = new int[m]; // 永远表示 group g 尚未完成的外部依赖边数量
        List<Integer>[] support = new ArrayList[n];
        for(int i = 0; i < n; i++) support[i] = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            List<Integer> beforeItem = beforeItems.get(i);
            taskDegree[i] = beforeItem.size();
            for(int item : beforeItem) {
                support[item].add(i);
                int currGroup = group[i];
                int nextGroup = group[item];
                if (nextGroup != currGroup && currGroup != -1) {
                    groupExternalDegree[currGroup]++;
                }
            }
        }

        // build group map
        List<Integer>[] groupMap = new ArrayList[m];
        for(int i = 0; i < m; i++) groupMap[i] = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            int currGroup = group[i];
            if(currGroup == -1) continue;
            groupMap[currGroup].add(i);
        }

        // init queue and ans
        Queue<Integer> readyGroup = new LinkedList<>(); // 记录无外部依赖的组
        Queue<Integer> readyNoGroupTask = new LinkedList<>(); // 记录无依赖的无组任务
        Queue<Integer> activeQueue = new LinkedList<>(); // 保证同组连续性
        // init readyNoGroupTask
        for(int i = 0; i < n; i++) {
            if(group[i] == -1 && taskDegree[i] == 0) readyNoGroupTask.offer(i);
        }
        // init readyGroup
        for(int i = 0; i < m; i++) {
            if(groupExternalDegree[i] == 0) readyGroup.offer(i);
        }
        int[] ans = new int[n];
        int ansIndex = 0;
        
        // process
        while(ansIndex < n) {
            int currGroup = -1;
            if(!readyNoGroupTask.isEmpty()) {
                while(!readyNoGroupTask.isEmpty()) activeQueue.offer(readyNoGroupTask.poll());
            } else if (!readyGroup.isEmpty()) {
                currGroup = readyGroup.poll();
                for(int i : groupMap[currGroup]) if(taskDegree[i] == 0) activeQueue.offer(i);
            } else break;

            while(!activeQueue.isEmpty()) {
                int curr = activeQueue.poll();
                ans[ansIndex++] = curr;
                for(int next : support[curr]) {
                    // update degree
                    taskDegree[next]--;

                    int nextGroup = group[next];
                    boolean crossGroup = nextGroup != currGroup && nextGroup != -1;

                    if (crossGroup) {
                        groupExternalDegree[nextGroup]--;
                    }

                    // process task ready
                    if (taskDegree[next] == 0) {
                        if (nextGroup == currGroup) {
                            activeQueue.offer(next);
                        } else if (nextGroup == -1) {
                            readyNoGroupTask.offer(next);
                        }
                    }

                    // process group ready
                    if (crossGroup && groupExternalDegree[nextGroup] == 0) {
                        readyGroup.offer(nextGroup);
                    }
                }
            }            
        }

        // return
        return ansIndex == n ? ans : new int[0];
    }
}