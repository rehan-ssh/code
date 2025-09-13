package com.github.dsa;

//https://leetcode.com/problems/wildcard-matching/
public class WildCardMatching {


    /**
     * logic
     * 
     * f(i, j) => will s1[0...i] matches with s2[0..j]
     * 
     * if(s1[i] == s[j] || s1[i] == '?'){
     * return f(i-1,j-1);
     * if(s1[i] == '*'){
     * return f(i, j-1) || f(i-1,j);
     * return false;
     * 
     * base case:
     * if(i<0 && j<0) return true;
     * else if(i<0 && j>=0) return false;
     * else if(j<0 && i>=0){
     * for(int a = 0; a<=i; a++){
     * if(s1[i] != *) return false;
     * }
     * return true;
     * }
     * 
     */

    public boolean isMatch(String s, String p) {

        int m = p.length(), n = s.length();
        boolean dp[][] = new boolean[m + 1][n + 1];

        dp[0][0] = true;
        for (int j = 1; j <= n; j++)
            dp[0][j] = false;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && p.charAt(i - 1) == '*';
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(i - 1) == s.charAt(j - 1) || p.charAt(i - 1) == '?')
                    dp[i][j] = dp[i - 1][j - 1];
                else if (p.charAt(i - 1) == '*')
                    // cant be optimized to 1 row, curr and prev required 2 rows
                    // because i-1, j-1 & i, j-1 both are needed together
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                else
                    dp[i][j] = false;
            }
        }

        return dp[m][n];

    }
}