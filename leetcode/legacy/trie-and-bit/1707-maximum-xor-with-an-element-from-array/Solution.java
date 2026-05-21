class TrieNode {
    public TrieNode[] next = new TrieNode[2];
}

class Trie {
    public int bitPos;
    public TrieNode root = new TrieNode();

    public Trie(int num) {
        bitPos = (num == 0) ? 0 : 31 - Integer.numberOfLeadingZeros(num);
    }

    public void add(int num) {
        TrieNode curr = root;
        for(int i = bitPos; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if(curr.next[bit] == null) {
                curr.next[bit] = new TrieNode();
            }
            curr = curr.next[bit];
        }
    }

    // 返回匹配的数,而不是异或的结果
    public int query(int num) {
        int ans = 0;
        TrieNode curr = root;

        for(int i = bitPos; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if(curr.next[bit ^ 1] != null) {
                bit ^= 1;
            }
            ans += bit << i;
            curr = curr.next[bit];
        }
        return ans;
    }
}

class Solution {
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[][] sortedQueries = buildSortedQueries(queries);
        int[] ans = new int[queries.length];
        Arrays.sort(nums);
        Trie trie = new Trie(nums[nums.length - 1]); // 题目保证了nums.length >= 1
        int numsIndex = 0;

        for(int i = 0; i < sortedQueries.length; i++) {
            int queryValue = sortedQueries[i][0];
            int queryM = sortedQueries[i][1];
            int queryIndex = sortedQueries[i][2];

            while(numsIndex < nums.length && nums[numsIndex] <= queryM) {
                trie.add(nums[numsIndex]);
                numsIndex++;
            }

            if(numsIndex == 0) {
                ans[queryIndex] = -1;
                continue;
            }
            ans[queryIndex] = queryValue ^ trie.query(queryValue);

        }

        return ans;
    }

    private int[][] buildSortedQueries(int[][] queries) {
        int[][] sortedQueries = new int[queries.length][3];
        for(int i = 0; i < queries.length; i++) {
            sortedQueries[i][0] = queries[i][0];
            sortedQueries[i][1] = queries[i][1];
            sortedQueries[i][2] = i;
        }

        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[1], b[1]));
        return sortedQueries;
    }
}