class Solution {
    public boolean isMatch(String s, String p) {
        int pBegin = 0;
        int pEnd = p.length() - 1;
        int sBegin = 0;
        int sEnd = s.length() - 1;
        
        // 保证p以'*'结尾
        while (pEnd >= 0 && sEnd >= 0) {
            char pChar = p.charAt(pEnd);
            if (pChar == '*') break;
            if (!charMatch(pChar, s.charAt(sEnd))) return false;
            pEnd--;
            sEnd--;
        }

        // 保证p以'*'开头
        while (pBegin <= pEnd && sBegin <= sEnd) {
            char pChar = p.charAt(pBegin);
            if (pChar == '*') break;
            if (!charMatch(pChar, s.charAt(sBegin))) return false;
            pBegin++;
            sBegin++;
        }
        if (pEnd < pBegin) return sEnd < sBegin;

        // 匹配主体部分
        while (pBegin <= pEnd && sBegin <= sEnd) {
            int currP = pBegin;
            int currS = sBegin;
            boolean match = true;

            // 尝试匹配一个块（currP至少会被末尾的‘*’兜住，所以不需要越界判断）
            while (p.charAt(currP) != '*') {
                if (currS > sEnd) return false; // s用尽p却还有剩余，直接失败
                if (!charMatch(p.charAt(currP), s.charAt(currS))) {
                    match = false;
                    break;
                }
                currS++;
                currP++;
            }

            // 判断是否匹配成功
            if (match) {
                pBegin = currP + 1;
                sBegin = currS;
            } else sBegin++;
        }

        // 如果有未被消耗的模式
        if (pBegin <= pEnd) {
            for (;pBegin <= pEnd; pBegin++) 
                if (p.charAt(pBegin) != '*') return false;
        }

        return true;
    }

    private boolean charMatch(char a, char b) {
        return a == '?' || a == b;
    }
}