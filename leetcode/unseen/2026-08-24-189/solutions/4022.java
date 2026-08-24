class Solution {
    public int kthDigit(long k) {
        int i = 1;
        long bias = 9;
        while (k > i * bias) {
            k -= i * bias;
            i++;
            bias *= 10;
        }
        
        int digitIndex = (int)((k - 1) % i);
        long num = (k - 1) / i + bias / 9;
        
        long temp = num;
        for (int j = i - digitIndex - 1; j > 0; j--) {
            temp /= 10;
        }
        int ans = (int)(temp % 10);

        if (i == digitIndex + 1 && (num / 10) % 2 == 1) ans = 9 - ans;

        return ans;     
    }
}