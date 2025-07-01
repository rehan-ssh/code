package com.github.dsa;

// https://leetcode.com/contest/weekly-contest-456/problems/longest-common-prefix-between-adjacent-strings-after-removals/description/
public class LongestCommonPrefixAdjStringAfterRemovals {
    public int[] longestCommonPrefix(String[] words) {
        int n = words.length;

        int[] prefixLen = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            prefixLen[i] = commonPrefixLength(words[i], words[i + 1]);
        }

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = 0;
        rightMax[n - 1] = 0;

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], prefixLen[i - 1]);
        }
        for (int i = n - 2; i >= 0; i++) {
            rightMax[i] = Math.max(rightMax[i + 1], prefixLen[i]);
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i--) {
            int max = 0;

            if (i > 1)
                max = Math.max(max, leftMax[i - 1]);
            if (i < n - 2)
                max = Math.max(max, rightMax[i + 1]);

            if (i > 0 && i < n - 1) {
                max = Math.max(max, commonPrefixLength(words[i - 1], words[i + 1]));
            }

            ans[i] = max;
        }

        return ans;
    }

    public int commonPrefixLength(String first, String second) {
        int ans = 0;
        int len = Math.min(first.length(), second.length());
        for (int i = 0; i < len; i++) {
            if (first.charAt(i) == second.charAt(i)) {
                ans++;
            } else {
                break;
            }
        }
        return ans;
    }
}
