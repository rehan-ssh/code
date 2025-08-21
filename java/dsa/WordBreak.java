package com.github.dsa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class WordBreak {
    // this is a 1D DP problem, we can use memoization to store results of
    // subproblems.
    int memo[];

    // Time Complexity: O(n^2 * m) where n is the length of the string and m is the
    // average length of words in the dictionary.
    // Space Complexity: O(n) for the memoization array.
    public boolean helper(int start, String s, Set<String> wordDict) {

        int n = s.length();

        if (start == n) {
            memo[start] = 1;
            return true;
        }

        if (memo[start] != 0)
            return memo[start] == 1;

        for (int end = start + 1; end <= n; end++) {
            String substr = s.substring(start, end);

            if (wordDict.contains(substr) && helper(end, s, wordDict)) {
                memo[start] = 1;
                return true;
            }
        }
        memo[start] = -1;
        return false;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        memo = new int[s.length() + 1];
        return helper(0, s, new HashSet<>(wordDict));
    }

    // tabulation solution
    public boolean wordBreakTabulation(String s, List<String> wordDict) {
        int n = s.length();
        boolean dp[] = new boolean[n + 1];
        dp[n] = true; // base case, empty string can always be segmented
        Set<String> wordSet = new HashSet<>(wordDict);
        for (int start = n - 1; start >= 0; start--) {
            for (int end = start + 1; end <= n; end++) {
                String substr = s.substring(start, end);
                if (wordSet.contains(substr) && dp[end]) {
                    dp[start] = true;
                    break; // no need to check further if we found a valid segmentation
                }
            }
        }
        return dp[0];
    }
}
