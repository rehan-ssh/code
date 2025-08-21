package com.github.dsa.Tree.Hard;

import com.github.dsa.Tree.TreeNode;

public class LCA {

    /**
     * - we will always return one value
     * - if we reach null we return null, if we reach any one of 2 values we return
     * from there
     * - so if the other value was beneath it we will keep returning the the top
     * value
     * - if other value was in other part of tree it will come as non null from
     * somewhere else
     * - if both children value is not null, then return the current value
     * 
     * 
     * 
     * 
     * * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
     */
    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode x, TreeNode y) {
            if (root == null)
                return null;
            if (root.val == x.val || root.val == y.val)
                return root;

            TreeNode left = lowestCommonAncestor(root.left, x, y);
            TreeNode right = lowestCommonAncestor(root.right, x, y);

            if (left != null && right != null)
                return root;
            if (left != null)
                return left;
            if (right != null)
                return right;
            return null;
        }
    }
}
