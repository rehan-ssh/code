package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.Arrays;

public class NumberOfLIS {

    class Solution {
        public int findNumberOfLIS(int[] arr) {
            int n = arr.length;
            int[] dp = new int[n];
            int[] cnt = new int[n];
            Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
            Arrays.fill(cnt, 1);
            int maxi=1;
            for (int i = 0; i < n; i++) {
                for (int prev = 0; prev < i; prev++) {
                    if (arr[prev] < arr[i] && dp[i]<1+dp[prev]) {
                        dp[i] = 1 + dp[prev];
                        //inherit
                        cnt[i] = cnt[prev];
                    }
                    else if(arr[prev] < arr[i] && dp[i] == 1+dp[prev]){
                        //increase
                        cnt[i]+=cnt[prev];
                    }
                }
                maxi = Math.max(dp[i], maxi);
            }
            int nos = 0;
            for(int i = 0; i < n; i++){
                if(maxi == dp[i]) nos+= cnt[i];
            }
            
            return nos;
    
            
        }
    }
    
}
