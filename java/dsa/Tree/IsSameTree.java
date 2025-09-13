package com.github.dsa.Tree;

public class IsSameTree {
    public boolean isSameTree(TreeNode r1, TreeNode r2) {
        if(r1==null && r2 ==null) return true;
        if(r1 == null || r2 == null) return false;
        if(r2.val!=r1.val) return false;
        
        boolean left = isSameTree(r1.left, r2.left);
        boolean right = isSameTree(r1.right, r2.right);
        return left&&right;

}
}
