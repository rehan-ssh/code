/**
 * @param {number[]} coins
 * @param {number} amount
 * @return {number}
 */
var coinChange = function (coins, amount) {
  const dp = new Array(coins.length + 1)
    .fill(0)
    .map(() => new Array(amount + 1).fill(0));
  coins.sort((a, b) => a - b);

  dp[0].fill(-1);
  dp[0][0] = 0;
  coins.forEach((coin, i) => {
    i++;
    dp[i][0] = 0;
    for (let j = 1; j < amount + 1; j++) {
      dp[i][j] =
        j - coin >= 0 && dp[i][j - coin] !== -1
          ? dp[i - 1][j] === -1
            ? dp[i][j - coin] + 1
            : Math.min(dp[i][j - coin] + 1, dp[i - 1][j])
          : dp[i - 1][j];
    }
  });
  return dp[coins.length][amount];
};
