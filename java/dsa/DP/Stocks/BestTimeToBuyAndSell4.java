package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell4 {
    // at most k transactions
    
    // only changed 2 to k in this solution
    class Solution {
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            int dp[][] = new int[2][k+1];
    
            for (int i = n - 1; i >= 0; i--) {
                int curr[][] = new int[2][k+1];
    
                for (int j = 1; j < k+1; j++) {
                    curr[1][j] = Math.max(-prices[i] + dp[0][j],
                            dp[1][j]);
    
                    curr[0][j] = Math.max(prices[i] + dp[1][j - 1],
                            dp[0][j]);
    
                }
                dp = curr;
            }
    
            return dp[1][k];
        }
    
    }
}
