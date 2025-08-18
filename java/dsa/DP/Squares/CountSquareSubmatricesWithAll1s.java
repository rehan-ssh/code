package com.github.dsa.DP.Partitions;

import java.util.stream.Stream;

public class CountSquareSubmatricesWithAll1s {

    /**
     * - we want to know how much each one is forming how many squares if he is the
     * bottom right
     * 
     * 
     * - we fill a dp table, first row and col, same as the given matrix and then we
     * put the min of top, left and top-left add one to it, if the square is 1, else
     * 0
     */
    class Solution {
        int countSquares(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            int[][] dp = new int[m][n];

            int sum = 0;

            for (int i = 0; i < m; i++) {
                dp[i][0] = matrix[i][0];
                sum += dp[i][0];
            }

            for (int j = 1; j < n; j++) {
                dp[0][j] = matrix[0][j];
                sum += dp[0][j];
            }

            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = matrix[i][j] == 0 ? 0
                            : 1 + Stream.of(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]).min(Integer::compare).get();
                    sum += dp[i][j];
                }
            }

            return sum;

        }
    }

}
