class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int i = 0;
        int n = intervals.length;
        List<int[]> ans = new ArrayList<>();

        while(i < n) {
            int[] interval = intervals[i];
            if(interval[1] >= newInterval[0]) break;
            ans.add(interval);
            i++;
        }

        while(i < n) {
            int[] interval = intervals[i];
            if(interval[0] > newInterval[1]) break;
            newInterval[0] = Math.min(newInterval[0], interval[0]);
            newInterval[1] = Math.max(newInterval[1], interval[1]);
            i++;
        }
        ans.add(newInterval);

        while(i < n) {
            int[] interval = intervals[i];
            ans.add(interval);
            i++;
        }

        return ans.toArray(new int[ans.size()][]);
    }
}