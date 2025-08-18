package com.github.dsa.Tree;

public class BalancedBinaryTree {
    // this is the sentinel approach- artificial placeholder that signals something
    // unusual
    int dfs(TreeNode root) {
        if (root == null)
            return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        if (left == -1 || right == -1)
            return -1;
        if (Math.abs(left - right) > 1)
            return -1;
        return Math.max(left, right) + 1;
    }

    // in interview go by this approach
    class Pair {
        int height;
        boolean isBalanced;
        Pair(int h, boolean isBalanced){
            this.height = h;
            this.isBalanced = isBalanced;
        }
    }

    Pair dfsP(TreeNode root) {
        if (root == null)
            return new Pair(0, true);
        Pair left = dfsP(root.left);
        Pair right = dfsP(root.right);
        boolean isBalanced = true;
        if (Math.abs(left.height - right.height) > 1 || !left.isBalanced || !right.isBalanced) {
            isBalanced = false;
        }

        return new Pair(Math.max(left.height, right.height) + 1, isBalanced);

    }

}
