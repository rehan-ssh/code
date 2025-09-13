package com.github.dsa;

public class Knapsack01 {
    int f(int index, int val[], int wt[], int w) {
        if (index == 0) {
            return w >= wt[0] ? val[0] : 0;
        }

        int notPick = f(index - 1, val, wt, w);
        int pick = w >= wt[index] ? val[index] + f(index - 1, val, wt, w - wt[index]) : 0;
        return Math.max(pick, notPick);
    }

    int knapsack(int n, int val[], int wt[], int w) {
        return f(n - 1, val, wt, w);
    }

    int knapsackdp(int n, int val[], int wt[], int w) {
        int[][] dp = new int[n][w + 1];

        for (int j = wt[0]; j <= w; j++) {
            dp[0][j] = val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = j >= wt[i] ? Math.max(dp[i - 1][j], val[i] + dp[i - 1][j - wt[i]]) : dp[i - 1][j];
            }
        }

        return dp[n - 1][w];
    }

    int knapsackso(int n, int val[], int wt[], int w) {
        // optimize this with one row only
        // what if we fill in reverse order the inner loop

        int[] dp = new int[w + 1];

        for (int j = wt[0]; j <= w; j++) {
            dp[j] = val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = w; j >= wt[i]; j--) {
                dp[j] = Math.max(dp[j], val[i] + dp[j - wt[i]]);
            }
        }

        return dp[w];
    }

}
