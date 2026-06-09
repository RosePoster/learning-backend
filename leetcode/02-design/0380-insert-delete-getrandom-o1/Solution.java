class RandomizedSet {
    
    private Map<Integer, Integer> map;
    private List<Integer> list;
    private Random random;

    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if(map.containsKey(val)) return false;
        int len = list.size();

        // 先add再put，顺序不能反，否则删除末尾元素时将出错
        list.add(val);
        map.put(val, len);

        return true;
    }
    
    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        int lastNumIndex = list.size() - 1;
        int lastNumVal = list.get(lastNumIndex);
        int deleteNumIndex = map.get(val);

        map.put(lastNumVal, deleteNumIndex);
        map.remove(val);

        list.set(deleteNumIndex, lastNumVal);
        list.remove(lastNumIndex);

        return true;
    }
    
    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */