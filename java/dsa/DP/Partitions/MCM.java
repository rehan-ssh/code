package com.github.dsa.DP.Partitions;

import java.util.Arrays;

public class MCM {

    /**
     * given the n matrix dimensions, tell the min cost to multiply
     * 
     * ABC ways to multiply A(BC) and (AB)C
     * 
     * ABCD ⇒ ways to multiply
     * 
     * A(B(CD)) or (AB)(CD) or A(BC)D…
     * 
     * given
     * 
     * dimension arr = 10, 20, 30, 40, 50
     * 
     * so from above A = 10*20, B = 20*30, C…D = 40*50
     * 
     * so for any ith matrix will be having dimension arr[i-1]*arr[i] eg A = 10*20
     * matrix [A being our 1st matrix i.e 1 indexed
     * 
     * 
     * logic:
     * f(i,j) f(1,n-1)
     * 
     * base case
     * if(i==j) return 0; // when we have single matrix
     * min = 1e9;
     * for(int k = i; k<=j-1, k++)
     * min = Math.min(min, f(i,k)+f(k+1,j)+A[i-1]*A[k]*A[j])
     * 
     * return min
     * // we can memoize of dp[i][j] - dp[n-1][n-1]
     * O(N*N(states) * N(for each state we run loop))
     * S.C. O(N) auxilary space
     */
    public static int mcm(int[] arr, int i, int j, int[][] dp) {
        if (i == j)
            return 0;
        if (dp[i][j] != -1)
            return dp[i][j];
        int min = Integer.MAX_VALUE;
        for (int k = i; k <= j - 1; k++) {
            int cost = mcm(arr, i, k, dp) + mcm(arr, k + 1, j, dp) + arr[i - 1] * arr[k] * arr[j];
            min = Math.min(min, cost);
        }
        return dp[i][j] = min;
    }

    public static int mcmTabulation(int[] arr, int n) {
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j < n; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
                    min = Math.min(min, cost);
                }
                dp[i][j] = min;
            }
        }
        return dp[1][n - 1];
    }

    public static void main(String[] args) {
        int[] arr = { 10, 20, 30, 40, 50 };
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
    }

}
