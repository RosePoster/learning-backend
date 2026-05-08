class MyStack {

    private Queue<Integer> stack;

    public MyStack() {
        stack = new LinkedList<>();
    }
    
    public void push(int x) {
        int size = stack.size();
        stack.offer(x);
        for(int i = 0; i < size; i++) {
            stack.offer(stack.poll());
        }
    }
    
    public int pop() {
        return stack.poll();
    }
    
    public int top() {
        return stack.peek();        
    }
    
    public boolean empty() {
        return stack.isEmpty();
    }
}
