package com.github.dsa.DP.Partitions;

public class PartitionArrayMaximumSum {
    /**
     * 
     * 
     * arr[] = [ 1, 15, 7, 9, 2, 5, 10] - k = 3 → max partition will have 3 elements
     * 
     * three patitions
     * 
     * if we decide 1, 15 and 7 9 2 and 5 10
     * 
     * then elements will become 15 15 and 9 9 9 and 10 10 i.e the max
     * 
     * so we get the sum = 77
     * logic:
     * f(i) => give me the max sum that you can make from 0 - f(0)
     * if(i==n) return 0;
     * 
     * len =0, max = intmin
     * maxsum = intmin
     * for(j = i, j< i+k && j < n; j++){
     * len++;
     * max = max(max, arr[j]);
     * sum= len*max + f(j+1);
     * maxsum = max(maxsum, sum);
     * }
     * return maxsum
     * 
     * // TC: O(n*k)
     * // SC: O(n)
     * 
     */

    // tabulation solution
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int len = 0;
            int max = Integer.MIN_VALUE;
            int maxsum = Integer.MIN_VALUE;
            for (int j = i; j < i + k && j < n; j++) {
                len++;
                max = Math.max(max, arr[j]);
                int sum = len * max + dp[j + 1];
                maxsum = Math.max(maxsum, sum);
            }
            dp[i] = maxsum;
        }

        return dp[0];

    }

}
