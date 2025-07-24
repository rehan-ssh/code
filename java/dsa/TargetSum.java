package com.github.dsa;

import java.util.HashMap;
import java.util.Map;

public class TargetSum {

    // this can be done by recurusion f(ind, T) assign + and - sign and do recursion
    // add them both to get the count

    // Memoization map to store (index, sum) -> count
    // way to memoize for negative numbers as array index can't be negative
    static Map<String, Integer> memo = new HashMap<>();

    public static int findTargetSumWays(int[] nums, int target) {
        memo.clear(); // Ensure it's empty before each call
        return dfs(0, 0, nums, target);
    }

    // Recursive helper function
    private static int dfs(int i, int sum, int[] nums, int target) {
        String key = i + "," + sum;
        if (memo.containsKey(key))
            return memo.get(key);

        if (i == nums.length) {
            return sum == target ? 1 : 0;
        }

        int plus = dfs(i + 1, sum + nums[i], nums, target);
        int minus = dfs(i + 1, sum - nums[i], nums, target);

        int count = plus + minus;
        memo.put(key, count);
        return count;
    }

    // do tabulation later

    // but we have done a problem where s1 - s2 = D
    // so we have done this problem where we wanted to put negative and positive
    // signs

}
