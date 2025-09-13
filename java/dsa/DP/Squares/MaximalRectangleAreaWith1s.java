package com.github.dsa.DP.Squares;

import java.util.ArrayDeque;

public class MaximalRectangleAreaWith1s {
    /**
     * given a rectangle, find the max area with all 1’s continuous
     */
    class Solution {
        int getAreaOfRowStack(int[][] dp, int k) {


            ArrayDeque<Integer> stack = new ArrayDeque<>();

            int n = dp[k].length;
            int[] heights = dp[k];

            int maxArea = 0;

            for (int j = 0; j <= n; j++) {
                int h = j == n ? 0 : heights[j];

                while (!stack.isEmpty() && h < heights[stack.peek()]) {
                    int rectHeight = heights[stack.pop()];
                    int rectWidth = 0;
                    if (!stack.isEmpty()) {
                        rectWidth = j - 1 - stack.peek();
                    } else {
                        rectWidth = j;
                    }

                    int area = rectHeight * rectWidth;
                    maxArea = Math.max(area, maxArea);

                }
                stack.push(j);

            }
            // in the end stack will have all zeros

            return maxArea;

        }

        public int maximalRectangle(char[][] matrix) {
            // we will go row wise and create a stack for each row if the prev row has a
            // number we will add it with current if there is no zero
            // then we will find the area of stacks for each row and return the max one
            int m = matrix.length;
            int n = matrix[0].length;
            int[][] dp = new int[m][n];

            for (int i = 0; i < n; i++) {
                if (matrix[0][i] == '1')
                    dp[0][i] = 1;
            }

            for (int i = 1; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == '1') {
                        dp[i][j] = dp[i - 1][j] + 1;
                    } else
                        dp[i][j] = 0;
                }
            }
            int max = (int) -1e9;
            for (int i = 0; i < m; i++) {
                max = Math.max(getAreaOfRowStack(dp, i), max);
            }

            return max;

        }

    }
}
