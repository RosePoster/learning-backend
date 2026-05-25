class Solution {
    class UnionFind {
        private int[] parent;

        UnionFind(int n) {
            parent = new int[n];
            for(int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int i) {
            if(parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if(rootI == rootJ) return false;
            parent[rootJ] = rootI;
            
            return true;
        }
    }

    class Translator {
        private Map<String, Integer> s2i;
        private Map<Integer, String> i2s;
        private int size;

        Translator(List<List<String>> accounts) {
            s2i = new HashMap<>();
            i2s = new HashMap<>();
            size = 0;
            for(List<String> account : accounts) {
                for(int i = 1; i < account.size(); i++) {
                    String email = account.get(i);
                    if(s2i.containsKey(email)) continue;
                    s2i.put(email, size);
                    i2s.put(size, email);
                    size++;
                }
            }
        }   

        public int getNum(String s) {
            return s2i.get(s);
        } 
        public String getString(int n) {
            return i2s.get(n);
        }
        public int getSize() {
            return size;
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // 初始化translator和unionfind 
        Translator translator = new Translater(accounts);
        int size = translator.getSize();
        UnionFind uf = new UnionFind(size);
        for(List<String> account : accounts) {
            for(int i = 2; i < account.size(); i++) {
                int emailNum1 = translator.getNum(account.get(i - 1));
                int emailNum2 = translator.getNum(account.get(i));
                uf.union(emailNum1, emailNum2);
            }
        }
        
        // 构建答案结构
        Map<Integer, List<String>> mergedAccounts = new HashMap<>();
        for(int i = 0; i < size; i++) {
            int parent = uf.find(i);
            mergedAccounts.computeIfAbsent(parent, k -> new ArrayList<>()).add(translator.getString(i));
        }

        // 建立下标与name映射
        Map<Integer, String> num2name = new HashMap<>();
        for(List<String> account : accounts) {
            String name = account.get(0);
            int num = uf.find(translator.getNum(account.get(1)));
            num2name.putIfAbsent(num, name);
        }
        
        // 将答案结构翻译为题目要求的格式
        List<List<String>> ans = new ArrayList<>();
        for(int num : mergedAccounts.keySet()) {
            List<String> emails = mergedAccounts.get(num);
            Collections.sort(emails);

            List<String> currAccount = new ArrayList<>();
            currAccount.add(num2name.get(num));
            for(String email : emails) currAccount.add(email);
            ans.add(currAccount);
        }
        
        return ans;
    }
}