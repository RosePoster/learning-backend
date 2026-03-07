class Solution {
    public int mostBooked(int n, int[][] meetings) {
        if(n == 1) {
            return 0;
        }
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));
        int[] meetingCount = new int[n];
        long[] endTime = new long[n];
        PriorityQueue<Integer> meetingRoom = new PriorityQueue<>(
            (a, b) -> {
                if(endTime[a] == endTime[b]) {
                    return a - b;
                }
                return Long.compare(endTime[a], endTime[b]);
            }
        );
        PriorityQueue<Integer> freeRoom = new PriorityQueue<>();
        
        for(int i = 0; i < n; i++) {
            freeRoom.offer(i);
        }

        for(int[] meeting : meetings) {
            long newEndTime = meeting[1];

            // 释放所有空闲房间
            while(!meetingRoom.isEmpty() && endTime[meetingRoom.peek()] <= meeting[0]) {
                freeRoom.offer(meetingRoom.poll());
            }

            // 找到编号最小的可用房间
            int room;
            if(freeRoom.isEmpty()) {
                room = meetingRoom.poll();
                newEndTime += endTime[room] - meeting[0];
            }else {
                room = freeRoom.poll();
            }            
            
            // 更新
            endTime[room] = newEndTime;
            meetingRoom.offer(room);
            meetingCount[room]++;
        }

        int ans = 0;
        for(int i = 0; i < n; i++) {
            if(meetingCount[i] > meetingCount[ans]) {
                ans = i;
            }
        }

        return ans;
    }
}