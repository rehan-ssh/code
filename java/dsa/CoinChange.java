package com.github.dsa;

public class CoinChange {
    int f(int index, int target, int[] coins) {
        if (index == 0) {
            if (target == 0)
                return 0;
            if (target % coins[0] == 0)
                return target / coins[0];
            return Integer.MAX_VALUE;
        }

        int notPick = f(index - 1, target, coins);
        int pick = Integer.MAX_VALUE;
        if (target >= coins[index]) {
            int subRes = f(index, target - coins[index], coins);
            if (subRes != Integer.MAX_VALUE)
                pick = subRes + 1;
        }

        return Math.min(pick, notPick);
    }

    int cc(int[] coins, int target) {
        int n = coins.length;
        return f(n - 1, target, coins);
    }

    int dp(int[] coins, int target) {
        int n = coins.length;
        int[] dp = new int[target + 1];

        for (int i = 0; i <= target; i++)
            if (i % coins[0] == 0)
                dp[i] = i / coins[0];
            else
                dp[i] = (int) 1e9;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int notPick = dp[j];
                int pick = (int) 1e9;
                if (j >= coins[i])
                    pick = dp[j - coins[i]] + 1;
                dp[j] = Math.min(pick, notPick);

            }
        }

        return dp[target] >= 1e9 ? -1 : dp[target];
    }
}
