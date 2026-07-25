/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    private Deque<State> stack;
    private Integer next;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new ArrayDeque<>();
        stack.push(new State(0, nestedList));
        getNext();
    }

    @Override
    public Integer next() {
        int ans = next;
        getNext();
        return ans;
    }

    private void getNext() {
        while (true) {
            while (!stack.isEmpty() && stack.peek().idx >= stack.peek().list.size()) stack.poll();
            if (stack.isEmpty()) {
                next = null;
                break;
            }
            State curr = stack.peek();
            NestedInteger item = curr.list.get(curr.idx++);
            if (item.isInteger()) {
                next = item.getInteger();
                break;
            } else {
                stack.push(new State(0, item.getList()));
            }
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    private static class State {
        int idx;
        List<NestedInteger> list;
        public State(int idx, List<NestedInteger> list) {
            this.idx = idx;
            this.list = list;
        }
    }
}


/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */