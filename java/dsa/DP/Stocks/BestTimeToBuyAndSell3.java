package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell3 {
    // 2 transactions only allowed

    /**
     * logic
     * 
     * f(ind, buy, cap) - f(9,1,2)
     * 
     * if(cap == 0) return 0;
     * if(ind == n) return 0;
     * 
     * if(buy)
     * return max (-prices[ind] + f(ind+1, 0, cap),
     * 0 + f(ind+1, 1, cap));
     * else{
     * return max (prices[ind] + f(ind+1, 1, cap-1),
     * 0 + f(ind+1, 0, cap);
     * 
     * }
     * TC: exponential
     * SC: O(n) - because of depth ASS
     * 
     * 
     * you acan also do f(ind, transactions=3) where even transactions represents 
     * buy and odd represent sell
     */

    class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int dp[][] = new int[2][3];

            for (int i = 0; i < 3; i++) {
                dp[0][i] = 0;// when you can sell
                dp[1][i] = 0;// when you can buy
            }

            for (int i = n - 1; i >= 0; i--) {
                int curr[][] = new int[2][3];

                for (int j = 1; j < 3; j++) {
                    curr[1][j] = Math.max(-prices[i] + dp[0][j],
                            dp[1][j]);

                    curr[0][j] = Math.max(prices[i] + dp[1][j - 1],
                            dp[0][j]);

                }
                dp = curr;
            }

            return dp[1][2];
        }

    }
}
