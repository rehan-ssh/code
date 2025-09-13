package com.github.dsa.DP.Partitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MinimumCostToCutStick {

    /*
     * given cuts array = [1, 3, 4, 5], n = 7
     * 
     * i can make a cut at any of the above values in any order
     * 
     * ---
     * logic:
     * - we will sort the array and put 0 and n at start and end[pad the array]
     * 
     * 0 1 3 4 5 7
     * ```java
     * f(1, 4) f(1, c)
     * 
     * // f(i,j) represents the minimum cost to cut the stick between cuts[i-1] and
     * cuts[j+1] considering only the cuts from index i to j
     * f(i, j)
     * if(i>j) return 0;
     * min = int_max;
     * for(int k = i; k <= j; k++)
     * cost = cuts[j+1] - cuts[i-1] + f(i, k-1) + f(k+1, j)
     * min = min(cost, min);
     * return min;
     * ```
     * 
     */

    // TC: O(c^3)
    // SC: O(c^2)
    public int minCost(int n, int[] cuts) {
        List<Integer> arrList = new ArrayList<>(
                Arrays.stream(cuts).boxed().collect(Collectors.toList()));

        arrList.add(0);
        arrList.add(n);
        Collections.sort(arrList);

        int c = cuts.length;

        int[][] dp = new int[c + 2][c + 2]; // as we added two vals

        for (int i = c; i >= 1; i--) {
            for (int j = i; j <= c; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    int cost = arrList.get(j + 1) - arrList.get(i - 1) + dp[i][k - 1] + dp[k + 1][j];
                    min = Math.min(cost, min);
                }
                dp[i][j] = min;
            }
        }

        return dp[1][c];

    }

}
