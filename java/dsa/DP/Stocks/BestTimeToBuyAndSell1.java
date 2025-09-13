package com.github.dsa.DP.Stocks;

public class BestTimeToBuyAndSell1 {
    /**
     * arr[] = 7,1,5,3,6,4
     * 
     * n =6
     * 
     * you have to decide a day when you buy and when you sell the stock
     * 
     * this is may be not dp, but basic constructive algo
     * 
     * but this is put under dp because the future problems builtup are dp
     */
    class Solution {
        public int maxProfit(int[] prices) {
            int min = prices[0];
            int maxProfit = 0;

            for (int i = 0; i < prices.length; i++) {
                maxProfit = Math.max(maxProfit, prices[i] - min);
                min = Math.min(min, prices[i]);
            }

            return maxProfit;

        }
    }
}
