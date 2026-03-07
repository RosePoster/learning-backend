class Solution {
    public int mostBooked(int n, int[][] meetings) {
        if(n == 1) {
            return 0;
        }

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        int[] meetingCount = new int[n];
        long[] endTime = new long[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (a, b) -> {
                if(endTime[a] == endTime[b]) {
                    return Integer.compare(a, b);
                }
                return Long.compare(endTime[a], endTime[b]);
            }
        );
        
        for(int i = 0; i < n; i++) {
            pq.offer(i);
        }

        for(int[] meeting : meetings) {
            int room = pq.poll();
            long newEndTime = meeting[1];
            while(endTime[room] < meeting[0]) {
                endTime[room] = meeting[0];
                pq.offer(room);
                room = pq.poll();
            }
            if(endTime[room] > meeting[0]) {
                newEndTime += endTime[room] - meeting[0];
            }
            endTime[room] = newEndTime;
            pq.offer(room);
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