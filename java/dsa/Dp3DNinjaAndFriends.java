package com.github.dsa;

import java.util.Arrays;

// https://leetcode.com/problems/cherry-pickup-ii/submissions/1705619877/
public class Dp3DNinjaAndFriends {

    class Solution {

        int dp[][][];

        public int collectChoclate(int i, int j1, int j2, int[][] g) {
            int m = g.length;
            int n = g[0].length;

            if (j1 < 0 || j1 > n - 1 || j2 < 0 || j2 > n - 1) {
                return Integer.MIN_VALUE;
            }
            if (i == m - 1) {
                if (j1 == j2)
                    return g[i][j1];
                else
                    return g[i][j1] + g[i][j2];
            }

            if (dp[i][j1][j2] != -1)
                return dp[i][j1][j2];

            int maxVal = Integer.MIN_VALUE;

            for (int a = -1; a <= 1; a++) {
                int alice = j1 + a;
                for (int b = -1; b <= 1; b++) {
                    int bob = j2 + b;
                    maxVal = Math.max(maxVal, collectChoclate(i + 1,
                            alice, bob, g));

                }
            }

            return dp[i][j1][j2] = (j1 == j2) ? maxVal + g[i][j1] : maxVal + g[i][j1] + g[i][j2];
        }

        public int cherryPickup(int[][] g) {
            // Your code goes here

            int m = g.length;
            int n = g[0].length;
            dp = new int[m + 1][n + 1][n + 1];

            for (int[][] grid : dp) {
                for (int[] row : grid) {
                    Arrays.fill(row, -1);
                }
            }

            return collectChoclate(0, 0, n - 1, g);
        }
    }

    class SolutionDP {

        int dp[][];

        public int cherryPickup(int[][] g) {
            // Your code goes here
            int m = g.length;
            int n = g[0].length;

            dp = new int[n + 1][n + 1];

            for (int j1 = 0; j1 <= n - 1; j1++) {
                for (int j2 = 0; j2 <= n - 1; j2++) {
                    dp[j1][j2] = (j1 == j2) ? g[m - 1][j1]
                            : g[m - 1][j1] + g[m - 1][j2];
                }
            }

            for (int i = m - 2; i >= 0; i--) {
                int[][] curr = new int[n + 1][n + 1];
                for (int j1 = 0; j1 < n; j1++) {
                    for (int j2 = 0; j2 < n; j2++) {
                        int maxVal = Integer.MIN_VALUE;
                        for (int a = -1; a <= 1; a++) {
                            int alice = j1 + a;
                            for (int b = -1; b <= 1; b++) {
                                int bob = j2 + b;
                                if (alice >= 0 && bob >= 0 && alice < n && bob < n)
                                    maxVal = Math.max(maxVal, dp[alice][bob]);

                            }
                        }

                        curr[j1][j2] = (j1 == j2) ? maxVal + g[i][j1] : maxVal + g[i][j1] + g[i][j2];

                    }
                }
                dp = curr;
            }

            return dp[0][n - 1];

        }
    }

}
