class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {    
        int[] inDegree = buildInDegree(recipes, ingredients, supplies);
        List<Integer>[] support = buildSupport(recipes, ingredients);

        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < inDegree.length; i++) {
            if(inDegree[i] == 0) queue.offer(i);
        }

        List<String> ans = new ArrayList<>();
        while(!queue.isEmpty()) {
            int curr = queue.poll();
            ans.add(recipes[curr]);
            for(int recipe : support[curr]) {
                inDegree[recipe]--;
                if(inDegree[recipe] == 0) queue.offer(recipe);
            }
        }

        return ans;
    }

    private int[] buildInDegree(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        int[] inDegree = new int[recipes.length];
        Set<String> sup = new HashSet<>();
        for(String s : supplies) sup.add(s);
        for(int i = 0; i < recipes.length; i++) {
            List<String> ingredient = ingredients.get(i);
            inDegree[i] = ingredient.size();
            for(String need : ingredient) {
                if(sup.contains(need)) inDegree[i]--;
            }
        }
        return inDegree;
    }

    private List<Integer>[] buildSupport(String[] recipes, List<List<String>> ingredients) {
        List<Integer>[] support = new ArrayList[recipes.length];
        for(int i = 0; i < recipes.length; i++) support[i] = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < recipes.length; i++) map.put(recipes[i], i);    

        for(int i = 0; i < recipes.length; i++) {
            List<String> ingredient = ingredients.get(i);
            for(String need : ingredient) {
                int n = map.getOrDefault(need, -1);
                if(n == -1) continue;
                support[n].add(i);
            }
        }

        return support;
    }
}