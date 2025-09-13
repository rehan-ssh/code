package com.github.dsa;

import java.util.Arrays;

public class SubsetSum {

    /**
     * Solution 1: Top-down recursion with memoization
     * Counts number of subsets that sum to target.
     */
    public static class RecursiveSubsetCounter {
        private int[][] memo;

        private int countSubsets(int[] arr, int target, int index) {
            if (target == 0)
                return 1;
            if (index == 0)
                return arr[0] == target ? 1 : 0;

            if (memo[index][target] != -1)
                return memo[index][target];

            int notTake = countSubsets(arr, target, index - 1);
            int take = 0;
            if (arr[index] <= target) {
                take = countSubsets(arr, target - arr[index], index - 1);
            }

            return memo[index][target] = take + notTake;
        }

        public boolean isSubsetSum(int[] arr, int target) {
            int n = arr.length;
            memo = new int[n][target + 1];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return countSubsets(arr, target, n - 1) >= 1;
        }
    }

    /**
     * Solution 2: Bottom-up space-optimized DP
     * Only checks whether any subset sums to target (true/false).
     */
    public static class SpaceOptimizedSubsetSum {

        public boolean isSubsetSum(int[] arr, int target) {
            int n = arr.length;
            if (n == 0)
                return false;

            boolean[] dp = new boolean[target + 1];
            dp[0] = true;

            if (arr[0] <= target) {
                dp[arr[0]] = true;
            }

            for (int i = 1; i < n; i++) {
                boolean[] curr = new boolean[target + 1];
                curr[0] = true;

                for (int j = 1; j <= target; j++) {
                    boolean notTake = dp[j];
                    boolean take = (j >= arr[i]) ? dp[j - arr[i]] : false;
                    curr[j] = take || notTake;
                }

                dp = curr;
            }

            return dp[target];
        }
    }

}
