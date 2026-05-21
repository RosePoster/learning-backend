class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int end = numbers.length - 1;
        int begin = 0;
        while(begin < end) {
            int curr = numbers[begin] + numbers[end];
            if(curr == target) return new int[]{begin + 1, end + 1};
            else if(curr < target) begin++;
            else end--;
        }
        return new int[2];
    }
}