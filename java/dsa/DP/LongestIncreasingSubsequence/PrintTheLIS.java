package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PrintTheLIS {


    //O(N2)
    public int[] printLis(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        // used for printing the lis
        // stores the previous element of this lis
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        int lastIndex = 0;
    
        for (int i = 0; i < n; i++) {
            hash[i] = i;
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i] && 1 + dp[prev] > dp[i]) {
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
        return lis.stream().mapToInt(i -> i).toArray();
    }
}
