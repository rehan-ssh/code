package com.github.dsa;

import java.util.Arrays;
import java.util.List;

public class Triangle {
    class Solution {

        int recur(List<List<Integer>> triangle, int m, int n) {
            if (m == triangle.size() - 1) {
                return triangle.get(m).get(n);
            }

            int down = recur(triangle, m + 1, n);
            int left = Integer.MAX_VALUE;
            if (n < triangle.get(m).size())
                left = recur(triangle, m + 1, n + 1);

            return triangle.get(m).get(n) + Math.min(down, left);
        }

        public int minimumTotal(List<List<Integer>> triangle) {
            return recur(triangle, 0, 0);
        }
    }

    class SolutionRecurDp{

        int dp[][];
        int recur(List<List<Integer>> triangle, int m, int n) {
            if (m == triangle.size() - 1) {
                return triangle.get(m).get(n);
            }
            if(dp[m][n]!=-1) return dp[m][n];
            int down = recur(triangle, m + 1, n);
            int left = Integer.MAX_VALUE;
            if (n < triangle.get(m).size())
                left = recur(triangle, m + 1, n + 1);

            return dp[m][n] = triangle.get(m).get(n) + Math.min(down, left);
        }

        public int minimumTotal(List<List<Integer>> triangle) {
            int m = triangle.size();
            dp = new int[m][m];
            for(int[] row: dp){
                Arrays.fill(row, -1);
            }
            return recur(triangle, 0, 0);

        }
    }

    class SolutionDP{
        public int minimumTotal(List<List<Integer>> triangle) {
            int m = triangle.size();
            int dp[][] = new int[m][m];

            dp[0][0] = triangle.get(0).get(0);

            for(int i = 1; i < m; i++){
                for(int j = 0; j <= i; j++){
                    int top = Integer.MAX_VALUE;
                    int left = Integer.MAX_VALUE;
                    if(j!=i){
                        top = dp[i-1][j];
                    }
                    if(j!=0){
                        left = dp[i-1][j-1];
                    }

                    dp[i][j] = triangle.get(i).get(j) + Math.min(top, left);
                }
            }

            int min = Integer.MAX_VALUE;
            for (int i = 0; i < m; i++) {
                min = Math.min(min, dp[m - 1][i]);
            }
            return min;

        }
    }

    class SolutionDPSpaceOpt{
        public int minimumTotal(List<List<Integer>> triangle) {
            int m = triangle.size();
            int dp[] = new int[m];

            dp[0] = triangle.get(0).get(0);

            for(int i = 1; i < m; i++){
                int [] curr = new int[m];
                for(int j = 0; j <= i; j++){
                    int top = Integer.MAX_VALUE;
                    int left = Integer.MAX_VALUE;
                    if(j!=i){
                        top = dp[j];
                    }
                    if(j!=0){
                        left = dp[j-1];
                    }

                    curr[j] = triangle.get(i).get(j) + Math.min(top, left);
                }
                dp = curr;
            }

            int min = Integer.MAX_VALUE;
            for (int i = 0; i < m; i++) {
                min = Math.min(min, dp[i]);
            }
            return min;

        }
    }
}
