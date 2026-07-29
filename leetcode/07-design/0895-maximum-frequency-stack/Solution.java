class FreqStack {
    private Map<Integer, Integer> freqMap;
    private Map<Integer, Deque<Integer>> freqStacks;
    private int maxFreq;

    public FreqStack() {
        freqMap = new HashMap<>();
        freqStacks = new HashMap<>();
        maxFreq = 0;
    }
    
    public void push(int val) {
        freqMap.merge(val, 1, Integer::sum);
        int freq = freqMap.get(val);
        Deque<Integer> stack = freqStacks.computeIfAbsent(freq, k -> new ArrayDeque<>());
        stack.push(val);
        maxFreq = Math.max(maxFreq, freq);
    }
    
    public int pop() {
        Deque<Integer> stack = freqStacks.get(maxFreq);
        int val = stack.pop();
        if (stack.isEmpty()) maxFreq--;
        freqMap.merge(val, -1, Integer::sum);
        return val;
    }
}