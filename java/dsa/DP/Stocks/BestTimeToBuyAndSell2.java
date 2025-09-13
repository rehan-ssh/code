package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell2 {

    /*
     * unlimited buys and sells allowed
     * 
     * logic
     * 
     * A lot of ways
     * We try all ways
     * This means recursion
     * 
     * Q) How to write recurrence
     * 1. express in terms of index
     * we have to know if at an ind something is already bought which is a state
     * f(ind, buy){
     * 2. explore posibilities
     * 3. take max of all profits
     * 
     * if(buy == false){
     * return profit = Math.max(-prices[ind] + f(ind+1, true), 0 + f(ind+1, false));
     * }
     * else{
     * return profit = Math.max( prices[ind]+f(ind+1, false), 0 + f(ind+1, true));
     * }
     * 
     * }
     * 4. Base case:
     * if(ind == n){
     * return 0;
     * }
     * 
     * f(0, false) -> starting with 0 and ability to buy or not buy
     */

    public int maxProfit(int[] prices) {
        int dp[] = new int[2];
        dp[0] = 0; //profit when we can buy of day n
        dp[1] = 0; //profit when we can sell on day n
        int n = prices.length;

        for (int i = n - 1; i >= 0; i--) {
            int prevProfitWhenWeCanBuy = dp[0];
            dp[0] = Math.max(-prices[i] + dp[1], dp[0]);
            dp[1] = Math.max(prices[i] + prevProfitWhenWeCanBuy, dp[1]);
        }

        return dp[0];

    }

    // here greedy also works
    // if there were transaction costs it won't work
    public int maxProfitG(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += prices[i] - prices[i - 1];
            }
        }
        return ans;
    }

}
