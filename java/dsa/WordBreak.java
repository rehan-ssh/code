package com.github.dsa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class WordBreak {

    int memo[];

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
}