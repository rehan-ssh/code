package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.Arrays;

public class LongestIncreasingSubsequence {

    /*
     * arr[] = [10, 9 , 2, 5, 3, 7, 101, 18]
     * 
     * 2, 3, 7, 101 - ans = 4
     * 
     * 8, 8, 8 → ans =1
     * logic
     * f(ind, prev_ind) => f(0,-1) => give the the LIS from 0 who has no prev
     * 
     * if(prev_ind==-1 || arr[ind]>arr[prev_ind){
     * return Math.max(1 + f(ind+1, ind), f(ind+1, prev_ind)); // pick, notpick
     * }
     * else{
     * return f(ind+1,prev_ind); //notpick
     * }
     * 
     * base case:
     * if(ind == n) return 0;
     */

    // for (int ind = n - 1; ind >= 0; ind--) {
    // for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {
    // if (prev_ind == -1 || arr[ind] > arr[prev_ind]) {
    // dp[ind][prev_ind + 1] = Math.max(
    // 1 + dp[ind + 1][ind + 1], // take
    // dp[ind + 1][prev_ind + 1] // not take
    // );
    /**
     * think of the above as
     * // dp[ind][prev_ind] = Math.max(
     * // 1 + dp[ind + 1][ind], // take ind
     * // dp[ind + 1][prev_ind] // not take ind
     * // );
     */

    // } else {
    // dp[ind][prev_ind + 1] = dp[ind + 1][prev_ind + 1]; // can't take
    // }
    // }
    // }

    // cleaned up version
    public int lengthOfLIS(int[] arr) {
        int n = arr.length;
        int[] prev = new int[n + 1];

        for (int ind = n - 1; ind >= 0; ind--) {
            int[] curr = new int[n + 1];
            for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {
                int notTake = prev[prev_ind + 1];
                int take = 0;
                if (prev_ind == -1 || arr[ind] > arr[prev_ind]) {
                    take = 1 + prev[ind + 1];
                }
                curr[prev_ind + 1] = Math.max(take, notTake);
            }
            prev = curr;
        }

        return prev[0]; // prev_ind == -1 corresponds to index 0
    }


    // simple O(N^2) soln
    public int lengthOfLISSimple(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
        int maxLen = 1;
    
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
    
        return maxLen;
    }

}
