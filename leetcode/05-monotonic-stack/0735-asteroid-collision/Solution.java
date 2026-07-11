class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        List<Integer> ans = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < asteroids.length; i++) {
            int asteroid = asteroids[i];
            if (asteroid < 0) {
                int size = -asteroid;
                while (!stack.isEmpty() && size > stack.peek()) {
                    stack.pop();
                }
                if (stack.isEmpty()) ans.add(asteroid);
                else if (size == stack.peek()) {
                    stack.pop();
                }
            } else {
                stack.push(asteroid);
            }
        }
        while (!stack.isEmpty()) ans.add(stack.pollLast());

        int[] arrAns = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            arrAns[i] = ans.get(i);
        }
        
        return arrAns;
    }
}