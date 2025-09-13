package com.github.dsa.DP.Partitions;

import java.util.Arrays;

public class BooleanExpressionEvaluation {
    /**
     * expression = T|T&F
     * 
     * expressions | or, & and, ^ xor
     * 
     * in how many ways can we evaluate the expression to true
     * 
     * ```java
     * f(0,n-1, 1) // n is the lenght of expression
     * 
     * f(i, j, 1) => we want the count of evaluations which makes the expression as
     * true
     * if(i>j) return 0;
     * if(i==j)
     * if(isTrue == 1) return a[i] =='T'? 1:0;
     * else return a[i] == 'F'?1:0;
     * int ways = 0;
     * for(int k = i+1; k <= j-1; k+=2){
     * int lt = f(i, k-1, 1); //leftTrue
     * int lf = f(i, k-1, 0);
     * int rt = f(k+1, j, 1);
     * int rf = f(k+1,j, 0);
     * if(a[k] == '&'){
     * if(isTrue) ways = ways + rt * lt;
     * else ways = ways + rt*lf + lf*rf + rf*lt;
     * 
     * }
     * 
     * else if(a[k] == '|'){
     * if(isTrue) ways = ways + rt * lt + rt*lf + rf*lt;
     * else ways = ways + lf*rf ;
     * }
     * 
     * else if(a[k] =='^'){
     * if(isTrue) ways = ways + rt*lf + rf*lt;
     * else ways = ways + lf*rf + rt * lt;
     * }
     * 
     * }
     * return ways
     * 
     * // this is memoized by 3 variable dp[n][n][2]
     * 
     * ```
     * 
     * TC: O(n^3)
     * SC: O(n^2)
     */

public class BooleanParenthesization {
    
    static int[][][] dp; // dp[i][j][isTrue]

    public static int countWays(String expr, int i, int j, int isTrue) {
        if (i > j) return 0;

        if (i == j) {
            if (isTrue == 1)
                return expr.charAt(i) == 'T' ? 1 : 0;
            else
                return expr.charAt(i) == 'F' ? 1 : 0;
        }

        if (dp[i][j][isTrue] != -1) return dp[i][j][isTrue];

        int ways = 0;

        for (int k = i + 1; k <= j - 1; k += 2) {
            int lt = countWays(expr, i, k - 1, 1);
            int lf = countWays(expr, i, k - 1, 0);
            int rt = countWays(expr, k + 1, j, 1);
            int rf = countWays(expr, k + 1, j, 0);

            char op = expr.charAt(k);

            if (op == '&') {
                if (isTrue == 1) ways += lt * rt;
                else ways += lt * rf + lf * rt + lf * rf;
            }
            else if (op == '|') {
                if (isTrue == 1) ways += lt * rt + lt * rf + lf * rt;
                else ways += lf * rf;
            }
            else if (op == '^') {
                if (isTrue == 1) ways += lt * rf + lf * rt;
                else ways += lt * rt + lf * rf;
            }
        }

        return dp[i][j][isTrue] = ways;
    }

    public static void main(String[] args) {
        String expr = "T|T&F^T";
        int n = expr.length();

        dp = new int[n][n][2];
        for (int[][] matrix : dp)
            for (int[] row : matrix)
                Arrays.fill(row, -1);

        int result = countWays(expr, 0, n - 1, 1);
        System.out.println("Number of ways: " + result);
    }
}

// write tabulation later

}
