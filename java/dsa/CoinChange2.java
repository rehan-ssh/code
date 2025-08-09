package com.github.dsa;

public class CoinChange2 {
    public class CoinChange {
        /**
         * psuedo recur code for writing the below
         * f(i, t, coins[]){
         * if(i==0){
         * if(t%coins[0]==0) return 1;
         * else return 0;
         * }
         * 
         * int notPick = f(i-1,t, coins);
         * int pick = t>=coins[i] ? f(i, t-coins[i], coins): 0;
         * 
         * 
         * return pick + notPick;
         * }
         */

        public static int countWays(int[] coins, int t) {
            int n = coins.length;
            int[] dp = new int[t + 1];

            // Base case: only using the first coin
            for (int i = 0; i <= t; i++) {
                dp[i] = (i % coins[0] == 0) ? 1 : 0;
            }

            // Process remaining coins
            for (int i = 1; i < n; i++) {
                for (int j = 0; j <= t; j++) {
                    int notPick = dp[j];
                    int pick = (j >= coins[i]) ? dp[j - coins[i]] : 0;
                    dp[j] = pick + notPick;
                }
            }

            return dp[t];
        }

        public static void main(String[] args) {
            int[] coins = { 1, 2, 5 }; // Example coins
            int target = 5;

            int ways = countWays(coins, target);
            System.out.println("Number of ways to make " + target + " = " + ways);
        }
    }

}
