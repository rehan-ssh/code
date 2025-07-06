package com.github.dsa;

import java.util.Arrays;
import java.util.stream.Stream;

public class MinFallingSumPath {
    public int minFallingPathSum(int[][] a) {
        int n = a[0].length;
        int m = a.length;
        int min = Integer.MAX_VALUE;

        int[] dp = new int[n];

        // base case
        for (int j = 0; j < n; j++)
            dp[j] = a[0][j];

        // observe i, j, convert to loops
        for (int i = 1; i < m; i++) {
            int curr[] = new int[n];
            for (int j = 0; j < n; j++) {
                int up = dp[j];
                int l = j > 0 ? dp[j - 1] : Integer.MAX_VALUE;
                int r = j < n - 1 ? dp[j + 1] : Integer.MAX_VALUE;
                curr[j] = a[i][j] + Stream.of(up, l, r).min(Integer::compareTo).orElseThrow();

            }
            dp = curr;
        }

        for (int i = 0; i < n; i++) {
            min = Math.min(min, dp[i]);
        }

        return min;

    }

    // recursive soln + memo
    class Solution {
        int dp[][];

        int f(int i, int j, int[][] a) {
            // base case ->

            int n = a[0].length;

            if (j < 0 || j >= n)
                return Integer.MAX_VALUE;
            if (i == 0)
                return a[0][j];
            if (dp[i][j] != -1)
                return dp[i][j];

            // explore paths
            int s = f(i - 1, j, a);
            int l = f(i - 1, j - 1, a);
            int r = f(i - 1, j + 1, a);

            return dp[i][j] = a[i][j] + Stream.of(s, l, r)
                    .min(Integer::compareTo)
                    .orElseThrow();
        }

        public int minFallingPathSum(int[][] matrix) {
            int min = Integer.MAX_VALUE;
            int n = matrix[0].length;
            int m = matrix.length;
            dp = new int[m][n];

            for (int row[] : dp) {
                Arrays.fill(row, -1);
            }

            for (int i = 0; i < n; i++) {
                min = Math.min(f(m - 1, i, matrix), min);
            }

            return min;
        }
    }
}
