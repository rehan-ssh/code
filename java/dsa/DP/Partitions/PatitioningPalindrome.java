package com.github.dsa.DP.Partitions;

public class PatitioningPalindrome {

    /**
     * logic:
     * 
     * String = bab abcba d c ede
     * 
     * how many minimal cuts to make all the substrings palindrome
     * 
     * in this case it is 4
     * 
     * 
     * logic:
     * f(i) - get min partitions from index i=0 -f(0)
     * if(i==n) return 0;
     * temp = "";
     * for(int j = i; j < n; j++){ // go over string
     * temp+=a[j];
     * if(i to j is a palindrome- isPalindrome(temp)){
     * min = Math.min(1+f(j+1), min);
     * }
     * 
     * }
     * return min;
     * 
     * // memoization dp[n]
     * 
     * this code is returning min segments, so to get cuts subtract 1 from ans
     * or return 0 for n-1 too
     * 
     * TC: O(n^2)
     * SC: O(n)
     * 
     * 
     */

    class Solution {
        boolean isPalindrome(String s) {

            int i = 0;
            int j = s.length() - 1;
            while (i < j) {
                if (s.charAt(i) != s.charAt(j))
                    return false;
                i++;
                j--;
            }
            return true;

        }

        public int minCut(String s) {
            int n = s.length();
            int[] dp = new int[n + 1];

            dp[n] = 0;

            for (int i = n - 1; i >= 0; i--) {
                StringBuilder sb = new StringBuilder();
                int min = Integer.MAX_VALUE;
                for (int j = i; j < n; j++) {
                    sb.append(s.charAt(j));
                    if (isPalindrome(sb.toString())) {
                        min = Math.min(1 + dp[j + 1], min);
                    }

                }
                dp[i] = min;

            }

            return dp[0] - 1;
        }
    }

}
