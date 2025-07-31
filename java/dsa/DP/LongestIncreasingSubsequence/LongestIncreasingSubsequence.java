package com.github.dsa.DP.LongestIncreasingSubsequence;

public class LongestIncreasingSubsequence {

    /*
     * arr[] = [10, 9 , 2, 5, 3, 7, 101, 18]
     * 
     * 2, 3, 7, 101 - ans = 4
     * 
     * 8, 8, 8 → ans =1
     * logic
     * f(ind, prev_ind) => f(0,-1) => give the the LIS from 0 who has no prev
     * 
     * if(prev_ind==-1 || arr[ind]>arr[prev_ind){
     * return Math.max(1 + f(ind+1, ind), f(ind+1, prev_ind));
     * }
     * else{
     * return f(ind+1,prev_ind);
     * }
     * 
     * base case:
     * if(ind == n) return 0;
     */

    class Solution {
        public int lengthOfLIS(int[] arr) {
            int n = arr.length;
            int prev[] = new int[n + 1];
            // base case is already 0

            for (int ind = n - 1; ind >= 0; ind--) {
                int curr[] = new int[n + 1];
                for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {

                    if (prev_ind == -1 || arr[ind] > arr[prev_ind]) {
                        // we are storing the second param in +1 state
                        curr[prev_ind + 1] = Math.max(1 + prev[ind + 1], prev[prev_ind + 1]);
                    } else {
                        curr[prev_ind + 1] = prev[prev_ind + 1];
                    }

                }
                prev = curr;
            }
            return prev[-1 + 1];
        }
    }

}
