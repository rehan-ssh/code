package com.github.dsa;

public class LongestCommonSubstring {

    /*
     * 
     * // main logic
     * f(int m, int n, int cnt){
     * if(m<0 or n<0) return cnt;
     * 
     * int res = cnt;
     * if(s1[m] == s2[n])
     * res = f(m-1, n-1,cnt+1);
     * 
     * res= max(res, max(f(m-1,n,0), f(m,n-1,0)));
     * 
     * return res;
     * }
     */
    public int longestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        int cnt = 0;
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : 0;
                cnt = Math.max(cnt, dp[i][j]);
            }
        }

        return cnt;
    }
}
