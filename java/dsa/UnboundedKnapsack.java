package com.github.dsa;

public class UnboundedKnapsack {

    /**
     * recursion used to get the belo
     * f(int[] wt, int[] val, int w, int ind){
     * if(ind == 0){
     * if(w<wt[0]) return 0;
     * if(w>=wt[0]) return w/wt[0] * val[0];
     * }
     * 
     * int notPick = f(wt, val, w, ind-1);
     * int pick = w>=wt[ind]? val[ind] + f(wt, val, w-wt[ind], ind): 0;
     * 
     * return Math.max(pick, notPick);
     * }
     * 
     * f(wt, val, w, n-1)
     */
    /**
     * Solves the Unbounded Knapsack problem using 1D dynamic programming.
     * 
     * @param wt  Array of item weights
     * @param val Array of item values
     * @param n   Number of items
     * @param t   Total capacity of the knapsack
     * @return Maximum value that can be obtained
     */
    public int solveUnboundedKnapsack(int[] wt, int[] val, int n, int t) {
        int[] dp = new int[t + 1];

        // Initialize dp[] using the first item
        for (int i = 0; i <= t; i++) {
            dp[i] = (i >= wt[0]) ? (i / wt[0]) * val[0] : 0;
        }

        // Process remaining items
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= t; j++) {
                int notPick = dp[j];
                int pick = (j >= wt[i]) ? val[i] + dp[j - wt[i]] : 0;
                dp[j] = Math.max(pick, notPick);
            }
        }

        return dp[t];
    }
}
