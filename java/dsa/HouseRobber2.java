package com.github.dsa;

public class HouseRobber2 {

    // Helper method to calculate max amount between indices [start, end]
    private int robLinear(int[] nums, int start, int end) {
        int prevTwo = 0;
        int prevOne = 0;

        for (int i = start; i <= end; i++) {
            int current = Math.max(prevOne, prevTwo + nums[i]);
            prevTwo = prevOne;
            prevOne = current;
        }

        return prevOne;
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return nums[0];

        // Either rob houses 0 to n-2 or houses 1 to n-1
        return Math.max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1));
    }
}
