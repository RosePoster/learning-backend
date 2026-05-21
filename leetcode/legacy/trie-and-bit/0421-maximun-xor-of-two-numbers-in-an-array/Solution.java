class TrieNode {
    public TrieNode[] next = new TrieNode[2];
}

class Trie {
    public TrieNode root = new TrieNode();
    public int maxBitPos;
    
    public Trie(int maxNum) {
        this.maxBitPos = (maxNum == 0) ? 0 : 31 - Integer.numberOfLeadingZeros(maxNum);
    }

    public void add(int num) {
        TrieNode curr = root;
        for(int i = maxBitPos; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if(curr.next[bit] == null) {
                curr.next[bit] = new TrieNode();
            }
            curr = curr.next[bit];
        }
    }
}

class Solution {
    public int findMaximumXOR(int[] nums) {
        int ans = 0;
        Trie trie = buildTrie(nums);

        for(int num : nums) {
            TrieNode curr = trie.root;
            int currMaxXor = 0;
            for(int i = trie.maxBitPos; i >= 0; i--) {
                int bit = (num >> i) & 1;
                int nBit = bit ^ 1;
                if(curr.next[nBit] != null) {
                    currMaxXor += (1 << i);
                    curr = curr.next[nBit];
                }else {
                    if((currMaxXor | ((1 << i) - 1)) <= ans) {
                        break;
                    }
                    curr = curr.next[bit];
                }
            }
            ans = Math.max(ans, currMaxXor);
        }
        return ans;
    }

    public Trie buildTrie(int[] nums) {
        int max = 0;
        for(int num : nums) {
            max = Math.max(max, num);
        }
        Trie trie = new Trie(max);
        for(int num : nums) {
            trie.add(num);
        }
        return trie;
    }
}