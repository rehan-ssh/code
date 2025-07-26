package com.github.dsa;

public class RodCutting {

    /**
     * logic required to build
     * f(ind, len, price)
     * if(ind==0){
     * if(len > 0) return len*price[0];
     * return 0;
     * }
     * 
     * notPick = f(ind-1, len, price);
     * pick = len>ind? price[ind] + f(ind, len-ind-1, price): 0;
     * 
     * return Math.max(pick, notPick);
     * 
     * 
     * 
     * f(n-1,len, price)
     */

    public static int cutRod(int[] price, int len) {
        int n = price.length;
        int[] dp = new int[len + 1];

        // Initialize for the first piece (rod length = 1)
        for (int l = 0; l <= len; l++) {
            dp[l] = l * price[0];
        }

        // Process remaining rod lengths
        for (int ind = 1; ind < n; ind++) {
            for (int l = ind+1; l <= len; l++) {
                int notPick = dp[l];
                int pick = price[ind] + dp[l - (ind + 1)];
                dp[l] = Math.max(pick, notPick);
            }
        }

        return dp[len];
    }

    public static void main(String[] args) {
        int[] price = { 2, 5, 7, 8, 10 };
        int len = 5;
        System.out.println("Maximum Profit: " + cutRod(price, len));
    }
}