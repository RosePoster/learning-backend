class Solution {
    public boolean canCross(int[] stones) {
        if (stones[1] > 1) return false;
        int n = stones.length;
        Map<Integer, Set<Integer>> states = new HashMap<>();
        states.computeIfAbsent(1, k -> new HashSet<>()).add(1);
        for (int i = 1; i < n - 1; i++) {
            if (!states.containsKey(i)) continue;
            Set<Integer> state = states.get(i);
            int currStone = stones[i];
            for (int stepLen : state) {
                int j = i + 1;
                while (j < n) {
                    int nextStone = stones[j];
                    if (nextStone > currStone + stepLen + 1) break;
                    if (nextStone >= currStone + stepLen - 1) {
                        states.computeIfAbsent(j, k -> new HashSet<>())
                            .add(nextStone - currStone);
                    }
                    j++;
                }
            }
            states.remove(i);
        }

        return states.containsKey(n - 1);
    }
}