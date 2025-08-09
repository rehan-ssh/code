package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LargestDivisibeSubset {

    // length 
    public int lengthOfLDSSimple(int[] arr) {
        int n = arr.length;
        Arrays.sort(arr);
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
        int maxLen = 1;
    
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[i] % arr[prev] == 0) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
    
        return maxLen;
    }

    // printing
    public List<Integer> largestDivisibleSubset(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.sort(arr);

        // used for printing the lis
        // stores the previous element of this lis
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        int lastIndex = 0;
    
        for (int i = 0; i < n; i++) {
            hash[i] = i;
            for (int prev = 0; prev < i; prev++) {
                if (arr[i] % arr[prev] == 0 && 1 + dp[prev] > dp[i]) {
                    dp[i] = 1 + dp[prev];
                    hash[i] = prev;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }
    
        ArrayList<Integer> lis = new ArrayList<>();
        lis.add(arr[lastIndex]);
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            lis.add(arr[lastIndex]);
        }
    
        Collections.reverse(lis);
        return lis;
    }
}
