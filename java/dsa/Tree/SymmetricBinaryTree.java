package com.github.dsa.Tree;

public class SymmetricBinaryTree {
    // symmetric from center
    // https://leetcode.com/problems/symmetric-tree/
    // - it converts to the problem of mirror image of a binary tree
    // - isMirrorImage(root.left, root.right)
    class Solution {

        boolean isMirrorImage(TreeNode t1, TreeNode t2) {
            if (t1 == null && t2 == null)
                return true;
            if (t1 == null || t2 == null)
                return false;
            if (t1.val != t2.val)
                return false;

            return isMirrorImage(t1.left, t2.right) && isMirrorImage(t1.right, t2.left);
        }

        public boolean isSymmetric(TreeNode root) {
            return isMirrorImage(root.left, root.right);
        }
    }
}
