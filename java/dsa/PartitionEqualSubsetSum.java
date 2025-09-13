package com.github.dsa;

public class PartitionEqualSubsetSum {
    class Solution {
        boolean isSubsetSum(int nums[], int index, int target) {
            boolean dp[][] = new boolean[index + 1][target + 1];

            for (int i = 0; i <= index; i++) {
                dp[i][0] = true;
            }
            if (nums[0] <= target)
                dp[0][nums[0]] = true;

            for (int i = 1; i <= index; i++) {
                for (int j = 1; j <= target; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= nums[i])
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }

            return dp[index][target];

        }

        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }

            if (sum % 2 == 1)
                return false;

            return isSubsetSum(nums, nums.length - 1, sum / 2);
        }
    }

    // failing soln due to prev = curr line both start pointing to same array
    // solution is to make a new array for curr in each iteration 1 to index
    boolean isSubsetSum(int nums[], int index, int target) {
        boolean prev[] = new boolean[target + 1];
        boolean curr[] = new boolean[target + 1];

        curr[0] = prev[0] = true;
        if (nums[0] <= target)
            prev[nums[0]] = true;

        for (int i = 1; i <= index; i++) {
            for (int j = 1; j <= target; j++) {
                curr[j] = prev[j];
                if (j >= nums[i])
                    curr[j] = prev[j] || prev[j - nums[i]];
            }
            prev = curr;
        }

        return prev[target];

    }

    // most space optimized here we loop from target to nums[i] in inner loop
    // this makes sure we are using the prev array values only
    // also for values < nums[i] we just keep the old array values intact
    boolean isSubsetSum(int[] nums, int target) {
        int n = nums.length;
        boolean[] dp = new boolean[target + 1];

        dp[0] = true; // sum of 0 is always possible (empty set)

        if (nums[0] <= target) {
            dp[nums[0]] = true; // base case for first number
        }

        for (int i = 1; i < n; i++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }

        return dp[target];
    }

}
