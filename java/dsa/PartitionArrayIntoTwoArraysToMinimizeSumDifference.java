package com.github.dsa;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference/description/
public class PartitionArrayIntoTwoArraysToMinimizeSumDifference {

    // when only positive integers are present we can use an array
    class Solution {

        // most space optimized here we loop from target to nums[i] in inner loop
        // this makes sure we are using the prev array values only
        // also for values < nums[i] we just keep the old array values intact
        int minDiffBetweenSubsets(int[] nums) {

            int n = nums.length;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += nums[i];
            }
            int target = sum;
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

            int min = Integer.MAX_VALUE;
            for (int i = 0; i <= target / 2; i++) {

                if (dp[i] == true) {
                    int diff = sum - i - i;
                    min = Math.min(min, diff);
                }
            }

            return min;

        }

        public int minimumDifference(int[] nums) {
            return minDiffBetweenSubsets(nums);
        }
    }

    // when negative numbers are possible and subset with 0 sum is allowed
    class Solution2 {

        // most space optimized here we loop from target to nums[i] in inner loop
        // this makes sure we are using the prev array values only
        // also for values < nums[i] we just keep the old array values intact
        int minDiffBetweenSubsets(int[] nums) {

            int n = nums.length;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += nums[i];
            }
            int target = sum;

            Set<Integer> possibleSums = new HashSet<>();


            possibleSums.add(0); // sum of 0 is always possible (empty set)

            if (nums[0] <= target) {
                possibleSums.add(nums[0]); // base case for first number
            }

            for (int i = 1; i < n; i++) {
                Set<Integer> newSums = new HashSet<>();
                for (int s : possibleSums) {
                    newSums.add(s);
                    newSums.add(s + nums[i]);
                }
                possibleSums = newSums;
            }

            int min = Integer.MAX_VALUE;
            for (int s : possibleSums) {
                int diff = Math.abs(sum - s - s);
                min = Math.min(min, diff);
            }

            return min;

        }

        public int minimumDifference(int[] nums) {
            return minDiffBetweenSubsets(nums);
        }
    }
}
