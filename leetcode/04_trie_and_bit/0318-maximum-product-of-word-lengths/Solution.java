class Solution {
    public int maxProduct(String[] words) {
        List<Map.Entry<Integer, Integer>> bitMasks = buildMask(words);
        int ans = 0;
        
        for(int i = 0; i < bitMasks.size(); i++) {
            int curr = bitMasks.get(i).getValue();
            for(int j = i + 1; j < bitMasks.size(); j++) {
                // If the bitwise AND of the two masks is not zero, it means they share at least one common character, so we skip this pair
                if((bitMasks.get(i).getKey() & bitMasks.get(j).getKey()) != 0) continue;
                // collect the product of the lengths of the two words and update the answer
                ans = Math.max(ans, curr * bitMasks.get(j).getValue());
            }
        }

        return ans;
    }

    private List<Map.Entry<Integer, Integer>> buildMask(String[] words) {
        Map<Integer, Integer> bitMasks = new HashMap<>();

        for(int i = 0; i < words.length; i++) {
            String word = words[i];
            int currMask = 0;
            for(int j = 0; j < word.length(); j++) {
                currMask |= (1 << word.charAt(j) - 'a');
            }
            // If the same mask already exists, we want to keep the longest word for that mask
            bitMasks.merge(currMask, word.length(), Math::max);
        }

        return new ArrayList<>(bitMasks.entrySet());
    }
}