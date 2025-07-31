package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell6 {
    /**
     * - stock with fees
     * 
     * there is a fees fixed eg 2 for each transaction
     * 
     * logic
     * 
     * f(ind, buy)
     * if(ind == 0) return 0;
     * if(buy){
     * return max(-price[ind] + f(ind+1, 0), f(ind+1,1);
     * }
     * else{
     * return max(price[ind] + f(ind+1, 1), f(ind+1, 0);
     * }
     * 
     * // in the normal code above we will pay fee either while buying or selling
     * 
     * else{
     * return max(price[ind] - fee + f(ind+1, 1), f(ind+1, 0);
     * }
     */
    class Solution {
        // you can space optimize later
        public int maxProfit(int[] prices, int fee) {

            int n = prices.length;
            int dp[][] = new int[n + 1][2];

            dp[n][0] = 0; // 0 means can buy or not buy
            dp[n][1] = 0; // 1 means can sell or not sell

            for (int i = n - 1; i >= 0; i--) {
                dp[i][0] = Math.max(-prices[i] + dp[i + 1][1], 0 + dp[i + 1][0]);

                dp[i][1] = Math.max(prices[i] - fee + dp[i + 1][0], 0 + dp[i + 1][1]);

            }

            return dp[0][0];

        }
    }

}
