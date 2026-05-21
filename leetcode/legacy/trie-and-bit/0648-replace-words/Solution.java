class Trie {
    boolean isEnd;
    Trie[] next;
    public Trie() {
        isEnd = false;
        next = new Trie[26];
    }

    // helper function to add a word to the trie
    public void addWord(String s) {
        Trie curr = this;

        for(int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if(curr.next[c] == null) {
                curr.next[c] = new Trie();
            }
            curr = curr.next[c];
        }

        curr.isEnd = true;
    }
}

class Solution {
    public String replaceWords(List<String> dictionary, String sentence) {
        Trie trieRoot = buildTrie(dictionary);
        StringBuilder sb = new StringBuilder();
        Trie curr = trieRoot;

        for(int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            
            // add the current character to the result
            sb.append(c);

            // only need to reset the trie here because we will skip the rest of the current word if we find a valid prefix or if there is no matching prefix in the dictionary
            if(c == ' ') {
                curr = trieRoot;
                continue;
            }
            
            // move the trie pointer to the next node corresponding to the current character
            curr = curr.next[c - 'a'];

            if(curr == null) {
                // if the trie pointer becomes null, it means there is no matching prefix in the dictionary
                // add the rest of the current word to the result and move the index to the end of the current word
                i = addRest(i, sentence, sb);
                continue;
            }else if(curr.isEnd) {
                // if the trie pointer is at the end of a word in the dictionary, it means we have found a valid prefix
                // skip the rest of the current word and move the index to the end of the current word
                i = continueRest(i, sentence);
            }            
        }

        return sb.toString();
    }

    // helper function to build a trie from the given dictionary
    private Trie buildTrie(List<String> dictionary) {
        Trie trie = new Trie();

        for(String s : dictionary) {
            trie.addWord(s);
        }

        return trie;
    }

    // helper function to move the index to the end of the current word without adding the characters to the result
    private int continueRest(int i, String sentence) {
        while(i + 1 < sentence.length() && sentence.charAt(i + 1) != ' ') {
            i++;
        }
        return i;
    }

    // helper function to move the index to the end of the current word and add the characters to the result
    private int addRest(int i, String sentence, StringBuilder sb) {
        while(i + 1 < sentence.length() && sentence.charAt(i + 1) != ' ') {
            i++;
            sb.append(sentence.charAt(i));
        }
        return i;
    }
}