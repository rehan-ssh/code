package com.github.dsa;

public class ShortestCommonSuperSequence {

    public String shortestCommonSupersequence(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int i = n;
        int j = m;
        StringBuilder ans = new StringBuilder();
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        while (i > 0 && j > 0) {
            if (s1[i - 1] == s2[j - 1]) {
                ans.append(s1[i - 1]);
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                ans.append(s1[i - 1]);
                i--;
            } else {
                ans.append(s2[j - 1]);
                j--;
            }
        }
        while (i > 0) {
            ans.append(s1[i - 1]);
            i--;
        }
        while (j > 0) {
            ans.append(s2[j - 1]);
            j--;
        }

        return ans.reverse().toString();

    }
}
