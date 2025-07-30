package com.github.dsa;

public class MinOperationsToConvertStringToAnother {

    // https://leetcode.com/problems/delete-operation-for-two-strings/description/
    /**
     * 
     * deletions = len1 - lcs
     * insertions = len2 - lcs
     * 
     * total len1+len2-2*lcs;
     * 
     */

    public int minDistance(String word1, String word2) {

        return word1.length() + word2.length() - 2 * lcs(word1, word2);

    }

    int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[] dp = new int[n + 1];

        for (int i = 1; i < m + 1; i++) {
            int[] curr = new int[n + 1];
            for (int j = 1; j < n + 1; j++) {
                curr[j] = s1.charAt(i - 1) == s2.charAt(j - 1) ? 1 + dp[j - 1] : Math.max(dp[j], curr[j - 1]);
            }
            dp = curr;
        }

        return dp[n];
    }

}
