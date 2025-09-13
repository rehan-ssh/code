package com.github.dsa.Tree;

public class MaximumPathSum {

    // very similar solution as the diameter problem, in diameter we updated the max
    // with lh+rh, here we update the max with lmax+rmax+val, and while returning we
    // don’t add one, we add node val
    class Solution {
        int maxSum = Integer.MIN_VALUE;

        int dfs(TreeNode root) {
            if (root == null)
                return 0;
            int left = Math.max(0, dfs(root.left));
            int right = Math.max(0, dfs(root.right));
            maxSum = Math.max(maxSum, left + right + root.val);
            return Math.max(left, right) + root.val;
        }

        public int maxPathSum(TreeNode root) {
            maxSum = Integer.MIN_VALUE;
            dfs(root);
            return maxSum;
        }
    }
}
