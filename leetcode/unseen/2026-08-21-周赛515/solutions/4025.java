class Solution {
    public int minPenalty(int period, int[] lights, int[] arrivalTime) {
        int max = 0;
        int ans = 0;
        for (int i = 0; i < lights.length; i++) max = Math.max(max, lights[i]);
        for (int i = 0; i < arrivalTime.length; i++) {
            int arrival = arrivalTime[i] % period;
            if (arrival < max) continue;
            ans = Math.max(ans, period - arrival);
        }
        return ans;
    }
}