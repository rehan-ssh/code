package com.github.dsa.Tree;

public class DiameterBinaryTree {

    /**
     * #
     * 
     * → longest path between 2 nodes - this is measured in edges not nodes so its
     * node cnt -1
     * 
     * → path does not need to pass via root
     * 
     * ans: find the left h and r h of each node, return the max(lh+rh)
     */

    int maxDiameter = 0;

    int findDiameter(TreeNode root) {

        if (root == null)
            return 0;
        int lh = findDiameter(root.left);
        int rh = findDiameter(root.right);

        maxDiameter = Math.max(lh + rh, maxDiameter);

        return Math.max(lh, rh) + 1;

    }

    public int getDiameter(TreeNode root) {
        findDiameter(root);
        return maxDiameter;
    }
}
