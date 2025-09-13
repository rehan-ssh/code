package com.github.dsa.Tree.Hard;

import com.github.dsa.Tree.TreeNode;

public class FlattenBinaryTree {
    TreeNode nextRight = null;

    void flatten(TreeNode root){
        if(root == null) return;
        
        flatten(root.right);
        flatten(root.left);
        
        
        root.left = null;
        root.right = nextRight;
        nextRight = root;
    }

}
