class StockSpanner {
    private Deque<State> prePrice;
    private int today;
    public StockSpanner() {
        prePrice = new ArrayDeque<>();
        today = -1;
    }
    
    public int next(int price) {
        State state = new State(price, ++today);
        while (!prePrice.isEmpty() && price >= prePrice.peek().price) {
            prePrice.pop();
        }
        int preDate = prePrice.isEmpty() ? -1 : prePrice.peek().date;
        prePrice.push(state);

        return today - preDate; 
    }
}

class State {
    int price;
    int date;
    State (int price, int date) {
        this.price = price;
        this.date = date;
    }
}