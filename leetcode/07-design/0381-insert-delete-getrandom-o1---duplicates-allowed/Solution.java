class RandomizedCollection {
    private Map<Integer, Set<Integer>> num2idx;
    private List<Integer> nums;
    private Random random;
    public RandomizedCollection() {
        num2idx = new HashMap<>();
        nums = new ArrayList<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        Set<Integer> idxs = num2idx.computeIfAbsent(val, k -> new HashSet<>());
        idxs.add(nums.size());
        nums.add(val);
        return idxs.size() > 1 ? false : true;
    }
    
    public boolean remove(int val) {
        Set<Integer> removeIdxs = num2idx.get(val);
        if (removeIdxs == null) return false;

        int lastIdx = nums.size() - 1;
        if (nums.get(lastIdx) == val) {
            removeIdxs.remove(lastIdx);
        } else {
            int idx = removeIdxs.iterator().next();
            int lastNum = nums.get(lastIdx);

            Set<Integer> lastIdxs = num2idx.get(lastNum);
            lastIdxs.remove(lastIdx);
            lastIdxs.add(idx);

            nums.set(idx, lastNum);
            removeIdxs.remove(idx);
        }

        nums.remove(lastIdx);

        if (removeIdxs.isEmpty()) {
            num2idx.remove(val);
        }
        return true;
    }
    
    public int getRandom() {
        return nums.get(random.nextInt(nums.size()));
    }
}