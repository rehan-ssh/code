package com.github.dsa;

public class MinInsertionsRequiredToMakeStringPalindrome {
    int f(String s) {
        int minInsertions = s.length() -
                longestCommonSubsequence(s,
                        new StringBuilder(s).reverse().toString());
        return minInsertions;
    }

    int longestCommonSubsequence(String s1, String s2) {
        // dp function for finding the longest common subseq
        return 0;
    }
}
