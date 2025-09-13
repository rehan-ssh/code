package com.github.dsa;

public class CountSubsetsWithSumK {

    class Solution {
        // for greater than 0
        int countSubsets(int[] arr, int index, int K) {

            if (index == 0) {
                if (K == 0 && arr[0] == 0)
                    return 2;
                if (K == 0 || arr[0] == K)
                    return 1;
                return 0;
            }

            int notPick = countSubsets(arr, index - 1, K);

            int pick = K >= arr[index] ? countSubsets(arr, index - 1, K - arr[index]) : 0;

            return pick + notPick;

        }

        public int perfectSum(int[] arr, int K) {
            return countSubsets(arr, arr.length - 1, K);

        }
    }

    public int perfectSum(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[n][K + 1];

        // Initialization
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1; // One way to make sum = 0 (empty subset)
        }

        if (arr[0] <= K)
            dp[0][arr[0]] += 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = dp[i - 1][j]; // not pick
                if (j >= arr[i])
                    dp[i][j] += dp[i - 1][j - arr[i]]; // pick
            }
        }

        return dp[n - 1][K];
    }

}
