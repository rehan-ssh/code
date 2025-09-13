package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.Arrays;

public class LongestStringChain {


    class Solution {

        boolean isPredecessor(String parent, String child){
            if(parent.length()!=child.length()+1)return false;
    
            int i = 0; 
            int j = 0;
    
    
            while(i<parent.length()){
                if(j< child.length() && parent.charAt(i) == child.charAt(j)){
                    i++;
                    j++;
                }
                else{
                    i++;
                }
            }
    
            return j == child.length();
        }
    
    
        public int longestStrChain(String[] arr) {
            int n = arr.length;
            Arrays.sort(arr, (a, b)->(a.length()-b.length()));
            int[] dp = new int[n];
            Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
            int maxLen = 1;
        
            for (int i = 0; i < n; i++) {
                for (int prev = 0; prev < i; prev++) {
                    if (isPredecessor(arr[i], arr[prev])) {
                        dp[i] = Math.max(dp[i], 1 + dp[prev]);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        
            return maxLen;
        }
    }
    
}
