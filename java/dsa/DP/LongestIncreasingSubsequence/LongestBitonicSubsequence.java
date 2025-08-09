package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.Arrays;

class LongestBitonicSubsequence {
    public int longestBitonicSubsequence(int[] arr) {
        int n = arr.length;

        // LIS from left to right
        int[] dp1 = new int[n];
        Arrays.fill(dp1, 1); // Each element is an LIS of length 1 by itself
    
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i]) {
                    dp1[i] = Math.max(dp1[i], 1 + dp1[prev]);
                }
            }
        }

        // LIS from right to left
        int[] dp2 = new int[n];
        Arrays.fill(dp2, 1); // Each element is an LIS of length 1 by itself

        for (int i = n - 1; i >= 0; i--) {
            for (int prev = n - 1; prev > i; prev--) {
                if (arr[prev] < arr[i]) {
                    dp2[i] = Math.max(dp2[i], 1 + dp2[prev]);
                }
            }
        }

        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, dp1[i] + dp2[i] - 1);
        }

        return maxLen;

    }

}