package com.github.dsa;

public class LCS {

    /*
     * below was written using this
     * f(ind1, ind2, s1, s2)
     * 
     * // base case
     * if(ind1<0 || ind2<0) return 0
     * 
     * // explore all posibilities
     * if (s1[ind1] == s2[ind2]) // match case
     * return 1 + f(ind1-1, ind2-1);
     * // not match case
     * return max(f(ind-1, ind2), f(ind1, ind2-1));
     */

    // Function to compute LCS length using space-optimized DP
    public int longestCommonSubsequence(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] dp = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int[] curr = new int[m + 1];
            for (int j = 1; j <= m; j++) {
                // if (s1[i] == s2[j]) // match case // here is one problem that i missed
                // it should be s1[i-1] and s2[j-1] because we are running loop on shifted index
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + dp[j - 1];
                } else {
                    curr[j] = Math.max(dp[j], curr[j - 1]);
                }
                // here we can't move to one arr sol becuase we need curr[j-1] and dp[j-1] both
            }
            dp = curr;
        }

        return dp[m];
    }

    // Main method to test the LCS function
    public static void main(String[] args) {
        // write the test cases here
        LCS lcs = new LCS();
        String s1 = "abcde";
        String s2 = "ace";
        int result = lcs.longestCommonSubsequence(s1, s2);
        System.out.println("Length of LCS: " + result); // Expected output: 3
        // Explanation: The LCS of "abcde" and "ace" is "ace", which has length 3.
    }
}
