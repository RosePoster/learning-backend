class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)) return 0;
        if(!wordList.contains(beginWord)) wordList.add(beginWord);

        Set<String> visited = new HashSet<>();
        Map<String, List<String>> map = buildMap(wordList, endWord);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        visited.add(beginWord);
        int ans = 1;
        while(!queue.isEmpty()) {
            int queueLen = queue.size();
            ans++;
            for(int i = 0; i < queueLen; i++) {
                String curr = queue.poll();
                List<String> nexts = map.get(curr);
                for(String next : nexts) {
                    if(next.equals(endWord)) return ans;
                    if(visited.contains(next)) continue;
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        return 0;
    }

    private Map<String, List<String>> buildMap(List<String> wordList, String endWord) {
        int len = endWord.length();

        Map<String, List<String>> helper = new HashMap<>();
        for(String s : wordList) {
            for(int i = 0; i < len; i++) {
                String pattern = s.substring(0, i) + "*" + s.substring(i + 1);
                helper.computeIfAbsent(pattern, k -> new ArrayList<>()).add(s);
            }
        }

        Map<String, List<String>> map = new HashMap<>();
        for(String s : wordList) {
            List<String> next = new ArrayList<>();
            for(int i = 0; i < len; i++) {
                String pattern = s.substring(0, i) + "*" + s.substring(i + 1);
                for (String nei : helper.get(pattern)) {
                    if (!nei.equals(s)) {
                        next.add(nei);
                    }
                }
            }
            map.put(s, next);
        }
        return map;
    }
}
