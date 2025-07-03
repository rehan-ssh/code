package com.github.dsa;

import java.util.Arrays;

public class MinSumPath {

    int[][] dp;

    int recur(int m, int n, int[][] grid) {
        if (m == 0 && n == 0) {
            return grid[m][n];
        }

        if (m < 0 || n < 0) {
            return Integer.MAX_VALUE;
        }

        if (dp[m][n] != -1)
            return dp[m][n];

        int up = recur(m - 1, n, grid);
        int left = recur(m, n - 1, grid);

        return dp[m][n] = grid[m][n] + Math.min(up, left);
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        dp = new int[m][n];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return recur(m - 1, n - 1, grid);
    }

    public int minPathIter(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
        
            int[][] dp = new int[m][n];
            dp[0][0] = grid[0][0];
        
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) continue;
        
                    int top = (i > 0) ? dp[i - 1][j] : Integer.MAX_VALUE;
                    int left = (j > 0) ? dp[i][j - 1] : Integer.MAX_VALUE;
        
                    dp[i][j] = grid[i][j] + Math.min(top, left);
                }
            }
        
            return dp[m - 1][n - 1];        
    }

    public int spaceOptimized(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[] dp = new int[n];

    for (int i = 0; i < m; i++) {
        int[] curr = new int[n];
        for (int j = 0; j < n; j++) {
            if (i == 0 && j == 0) {
                curr[j] = grid[0][0];
            } else {
                int top = (i > 0) ? dp[j] : Integer.MAX_VALUE;
                int left = (j > 0) ? curr[j - 1] : Integer.MAX_VALUE;
                curr[j] = grid[i][j] + Math.min(top, left);
            }
        }
        dp = curr;
    }

    return dp[n - 1];            
}
}
