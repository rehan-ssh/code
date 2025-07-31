package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell5 {

    /**
     * logic
     * 
     * → buy and sell with a cool down
     * 
     * → cannot buy right after sell
     * 
     * prices[] = {4,9,0,4,10}
     * 
     * 9-4 + 10-4 = 11
     * 
     * //normal buy and sell unlimited
     * 
     * f(ind, buy)
     * 
     * if(ind >= n) return 0;
     * 
     * if(buy){
     * return profit = min(-prices[i] + f(ind+1, 0), f(ind+1, 1))
     * }
     * else{
     * return profit = min (prices[i]+f(ind+1,1), f(ind+1,0));
     * }
     * 
     * // the only change here is in selling now
     * //you can't buy on the next day
     * else{
     * return profit = min(prices[i]+f(ind+2,1), f(ind+1,0));
     * }
     */

    class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int dp[][] = new int[n + 2][2];

            dp[n][0] = 0; // 0 means can buy or not buy
            dp[n][1] = 0; // 1 means can sell or not sell

            for (int i = n - 1; i >= 0; i--) {
                dp[i][0] = Math.max(-prices[i] + dp[i + 1][1], 0 + dp[i + 1][0]);
                // for space op we will require three size 2 arrays are we have i+1 and i+2
                dp[i][1] = Math.max(prices[i] + dp[i + 2][0], 0 + dp[i + 1][1]);

            }

            return dp[0][0];

        }
    }
}
