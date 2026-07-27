class CustomStack {
    private int[] def;
    private int top; 
    public CustomStack(int maxSize) {
        def = new int[maxSize + 1];
        top = 0;
    }
    
    public void push(int x) {
        if (top >= def.length - 1) return;
        def[top] = x - def[top];
        def[++top] = x;
    }
    
    public int pop() {
        if (top <= 0) return -1;
        int curr = def[top];
        top--;
        def[top] = curr - def[top];
        return curr;
    }
    
    public void increment(int k, int val) {
        if (top == 0) return;
        def[0] += val;
        if (k >= top) def[top] += val;
        else def[k] -= val;
    }
}

/**
差分法

    private int[] def;
    private int top; 
    public CustomStack(int maxSize) {
        def = new int[maxSize + 1];
        top = 0;
    }
    
    public void push(int x) {
        if (top >= def.length - 1) return;
        def[top] = x - def[top];
        def[++top] = x;
    }
    
    public int pop() {
        if (top <= 0) return -1;
        int curr = def[top];
        top--;
        def[top] = curr - def[top];
        return curr;
    }
    
    public void increment(int k, int val) {
        if (top == 0) return;
        def[0] += val;
        if (k >= top) def[top] += val;
        else def[k] -= val;
    }
}

 */