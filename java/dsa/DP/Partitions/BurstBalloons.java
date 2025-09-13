package com.github.dsa.DP.Partitions;

public class BurstBalloons {

    /**
     * arr[] = 3, 1, 5, 8
     * 
     * if we burst a ballon we get left*i*right
     * 
     * if i burst 5 i will get 1*5*8 = 40
     * 
     * for the left most balloon we take left as 1, similar for right
     * 
     * ```java
     * 
     * pad the array for ease
     * 1, 3, 1, 5, 8, 1
     * 
     * f(1, 4)
     * f(1, c)
     * f(i,j) - max cost to burst balloons from i to j
     * if(i>j) return 0;
     * max =int_min
     * for(int k = i; k<=j; k++){
     * cost = balloon[i-1]*balloon[k]*balloon[j+1] + f(i, k-1) + f(k+1,j)
     * max = max(max,cost);
     * }
     * return max;
     * 
     * ```
     * 
     * here we are saying balloon[k] is the last balloon i am bursting from range i
     * to j, so it will depend on 1, 1 i.e the boundaries
     * 
     * then the second last balloon i will burst will have the boundaries i-1 and k
     * because we burst the kth balloon in the iteration
     * 
     * so f(i,k-1) gives me the best bursting max value of i to k-1 balloons and i
     * know the boundaries as i-1 and k
     * 
     * if you think logically also the balloon which is last burst will only depend
     * on the left and right boundary and will add the cost of itself only,
     * similarly the second last will add the mult of itself and last, and so on
     * 
     * TC: O(c^3)
     * SC: O(c^2)
     * 
     * 
     */

    class Solution {
        public int maxCoins(int[] balloons) {

            int[] arr = new int[balloons.length + 2];
            int idx = 0;
            arr[idx] = 1;
            idx++;
            for (int j = 0; j < balloons.length; j++) {
                arr[idx] = balloons[j];
                idx++;
            }
            arr[idx] = 1;

            int c = balloons.length;

            int[][] dp = new int[c + 2][c + 2]; // as we added two vals

            for (int i = c; i >= 1; i--) {
                for (int j = i; j <= c; j++) {
                    int max = Integer.MIN_VALUE;
                    for (int k = i; k <= j; k++) {
                        int cost = arr[i - 1] * arr[k] * arr[j + 1] + dp[i][k - 1] + dp[k + 1][j];
                        max = Math.max(cost, max);
                    }
                    dp[i][j] = max;
                }
            }

            return dp[1][c];

        }
    }

}
