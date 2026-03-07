class Solution {
    public int leastInterval(char[] tasks, int n) {
        if(n < 1) return tasks.length;
        Map<Character, Integer> count = new HashMap<>();
        for(char c : tasks) {
            count.merge(c, 1, Integer::sum);
        }

        int[] endTime = new int[26];

        PriorityQueue<Character> freeTasks = new PriorityQueue<>(
            (a, b) -> Integer.compare(count.get(b), count.get(a))
        );
        for(char task : count.keySet()) {
            freeTasks.offer(task);
        }

        Queue<Character> freezeTasks = new LinkedList<>();

        int time = 0;
        while(!freeTasks.isEmpty() || !freezeTasks.isEmpty()) {
            // 取出所有可执行任务
            while(!freezeTasks.isEmpty() && endTime[freezeTasks.peek() - 'A'] < time) {
                freeTasks.offer(freezeTasks.poll());
            }

            // 取出当前可执行任务
            char task;
            if(freeTasks.isEmpty()) {
                task = freezeTasks.poll();
                time = endTime[task - 'A'] + 1;
            }else {
                task = freeTasks.poll();
            }

            // 执行
            endTime[task - 'A'] = time + n;
            time++;
            count.put(task, count.get(task) - 1);

            // 更新
            if(count.get(task) > 0)  {
                freezeTasks.offer(task);
            }
        }

        return time;
    }
}