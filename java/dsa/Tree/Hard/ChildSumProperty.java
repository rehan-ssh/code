package com.github.dsa.Tree.Hard;

import com.github.dsa.Tree.TreeNode;

public class ChildSumProperty {
    class Solution {

        // intuition is while going down in the recursion make the children greater than
        // or equal to parent so that while coming up there is no need to reduce the
        // parent
        void dfs(TreeNode root) {
            if (root == null)
                return;
            int val = 0;
            if (root.left != null)
                val += root.left.val;
            if (root.right != null)
                val += root.right.val;

            if (root.val > val) {
                // atleast update one child to root.val, no harm in updating both
                if (root.left != null)
                    root.left.val = root.val;
                if (root.right != null)
                    root.right.val = root.val;
            }

            dfs(root.left);
            dfs(root.right);

            int total = 0;
            if (root.left != null)
                total += root.left.val;
            if (root.right != null)
                total += root.right.val;
            if (root.left != null || root.right != null)
                root.val = total; // atleast has one child
            return;

        }

        void updateChildrenSum(TreeNode root) {
            dfs(root);
        }
    }

}
