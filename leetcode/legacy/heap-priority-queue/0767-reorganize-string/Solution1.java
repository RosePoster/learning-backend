class Solution {
    public String reorganizeString(String s) {
        // 初始化计数器
        int[] counter = new int[27];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
        }

        // 初始化最大堆
        PriorityQueue<Integer> maxHeep = new PriorityQueue<>(
            (a, b) -> Integer.compare(counter[b], counter[a])
        );

        for (int i = 0; i < 26; i++) {
            if(counter[i] > 0) {
                maxHeep.offer(i);
            }
        }

        // 初始化辅助数据结构
        StringBuilder sb = new StringBuilder();
        int preC = 26;

        // 执行
        while(sb.length() < s.length()) {
            // 无可用字符，排列失败
            if(maxHeep.isEmpty()) {
                sb.setLength(0);
                break;
            }

            // 取出一个字符并排列
            int c = maxHeep.poll();
            sb.append((char)('a' + c));
            counter[c]--;
            if(counter[preC] > 0) {
                maxHeep.offer(preC);
            }
            preC = c;
        }

        // return
        return sb.toString();
    }
}