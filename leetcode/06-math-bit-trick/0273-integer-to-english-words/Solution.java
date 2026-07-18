class Solution {
    private static final String[] BASE = {
        "", "One ", "Two ", "Three ", "Four ",
        "Five ", "Six ", "Seven ", "Eight ", "Nine ",
        "Ten ", "Eleven ", "Twelve ", "Thirteen ", "Fourteen ",
        "Fifteen ", "Sixteen ", "Seventeen ", "Eighteen ", "Nineteen "
    };

    private final static String[] TEN = {
        "", "", "Twenty ", "Thirty ", "Forty ", "Fifty ", 
        "Sixty ", "Seventy ", "Eighty ", "Ninety "
    };

    private final static String[] LEVEL = {
        "", "Thousand ", "Million ", "Billion "
    };

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        StringBuilder[] ans = new StringBuilder[4];
        int level = 0;
        
        for (; num > 0; num /= 1000) {
            StringBuilder curr = parseSection(num % 1000);
            if (curr.length() != 0) {
                curr.append(LEVEL[level]);
            }
            ans[level] = curr;
            level++;
        }
        StringBuilder words = new StringBuilder();
        for (int i = 3; i >= 0; i--) if (ans[i] != null) words.append(ans[i]);
        words.deleteCharAt(words.length() - 1);
        
        return words.toString();
    }

    private StringBuilder parseSection(int num) {
        StringBuilder sb = new StringBuilder();
        int hundredsDigit = num / 100;
        int tensDigit = num / 10 % 10;
        int onesDigit = num % 10;

        if (hundredsDigit >= 1) {
            sb.append(BASE[hundredsDigit]).append("Hundred ");
        }

        if (tensDigit > 1) {
            sb.append(TEN[tensDigit]);
            sb.append(BASE[onesDigit]);
        } else {
            sb.append(BASE[tensDigit * 10 + onesDigit]);
        }

        return sb;
    }
}