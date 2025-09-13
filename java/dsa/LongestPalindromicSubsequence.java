package com.github.dsa;

public class LongestPalindromicSubsequence {
    // longest common subsequence between string and reverse(string)

    int f(String s) {
        int longestPalindromicSubsequence =
                longestCommonSubsequence(s,
                        new StringBuilder(s).reverse().toString());
        return longestPalindromicSubsequence;
    }

    int longestCommonSubsequence(String s1, String s2) {
        // dp function for finding the longest common subseq
        return 0;
    }
}
