package com.github.dsa;


// https://leetcode.com/problems/edit-distance/
public class EditDistance {

    /**
     * Que:
     * min operations to convert s1 to s2
     * 
     * three ops allowed 1. insert, 2.remove, 3.replace
     * 
     * s1= “horse”, s2 = “ros” ⇒ 3 operations
     * 
     * - in prev question we had done insert and remove using lcs
     * - this question adds replace so it becomes more difficult
     * 
     * logic:
     * lcs is not working directly like insert and remove
     * so next thing is try all possible ways i.e. recursion
     * 
     * f(i,j) // min operations required to convert s1[0...i] to s2[0...j]
     * 
     * if(s[i] == s[j]){
     * return 0 + f(i-1,j-1)
     * }
     * 
     * // if not matched
     * // delete the char, insert, replace
     * return Math.min(1+f(i-1,j), 1+f(i,j-1), 1+f(i-1,j-1))
     * 
     * 
     * base case:
     * if(i<0) return j+1;
     * if(j<0) return i+1;
     * 
     * 
     */

    public int editDistance(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // Base case: converting to/from empty strings
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= m; i++) {
            dp[0][i] = i;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1)
                        ? dp[i - 1][j - 1]
                        : 1 + Math.min(
                                Math.min(dp[i - 1][j], // delete
                                        dp[i][j - 1]), // insert
                                dp[i - 1][j - 1] // replace
                        );
            }
        }

        return dp[n][m];
    }

}
