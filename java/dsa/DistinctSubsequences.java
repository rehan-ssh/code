package com.github.dsa;

//https://leetcode.com/problems/distinct-subsequences/description/
public class DistinctSubsequences {

    /**
     * question
     * s = “babgbag”, s2 = “bag”
     * 
     * - given two strings how many times s2 comes in s1
     * - here it is 5 occurrences
     * 
    /**
     * logic
     * f(i, j){ no. of distinct subsequences of s2[0..j] in s1[0..i]
     * 
     * cnt = 0;
     * if(s1[i] == s2[j]){
     * cnt+=f(i-1,j-1)
     * }
     * cnt += f(i-1,j)
     * return cnt
     * 
     * base case:
     * if(i<0) return 0;
     * if(j<0) return 1;
     * 
     */

    // we can do one array here just start the loop two from m to 1
    public int distinctSubsequences(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // 1. base case
        // do 1 based indexing
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        // 2. write changing params
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 3. copy the recurrence
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
                dp[i][j] += dp[i - 1][j];
            }
        }

        return dp[n][m];
    }

}
