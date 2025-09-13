package com.github.dsa.DP.LongestIncreasingSubsequence;

import java.util.ArrayList;
import java.util.List;

public class LISUsingBinarySearch {
    // O(N log N) solution for LIS using binary search
    // not the next greater, need a lower bound here not an upper bound
    int getNextGreaterOrEqualElemIndex(List<Integer> lis, int x) {
        int n = lis.size();
        int low = 0;
        int high = n - 1;
        int ans = n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (lis.get(mid) > x) {
                ans = mid;
                high = mid - 1;
            } else if (lis.get(mid) <= x)
                low = mid + 1;

        }

        return ans;
    }

    int lis(int[] arr) {

        List<Integer> lis = new ArrayList<>();

        for (int x : arr) {
            int lisLen = lis.size();
            if (lisLen == 0)
                lis.add(x);
            else if (lis.get(lisLen - 1) < x)
                lis.add(x);
            else {
                int idx = getNextGreaterOrEqualElemIndex(lis, x);
                lis.set(idx, x);
            }
        }

        return lis.size();

    }
}
