package com.github.dsa;
public class UniquePathsObstacles {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[] dp = new int[n];
        dp[0] = obstacleGrid[0][0] == 1 ? 0 : 1;

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    curr[j] = 0;
                } else if (j == 0) {
                    curr[j] = dp[0];
                } else {
                    curr[j] = dp[j] + curr[j - 1];
                }
            }
            dp = curr;
        }

        return dp[n - 1];
    }
}
