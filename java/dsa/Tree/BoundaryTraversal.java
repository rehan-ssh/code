package com.github.dsa.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BoundaryTraversal {



    boolean isLeaf(TreeNode root){
        return root.left == null && root.right == null;
    }
    void addRightSide(TreeNode root, List<Integer> ans){
        if(root == null) return;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        root = root.right;

        while(root!=null){
            if(!isLeaf(root)) stack.push(root.val);

            if(root.right != null)
                root = root.right;
            else{
                root = root.left;
            }
        }

        while(!stack.isEmpty()){
            ans.add(stack.pop());
        }
    }


    void addLeaves(TreeNode root, List<Integer> ans){
        if(root == null) return;

        if(isLeaf(root)){
            ans.add(root.val);
        }
        addLeaves(root.left, ans);
        addLeaves(root.right, ans);

    }


    void addLeftSide(TreeNode root, List<Integer> ans){
        if(root == null) return;

        while(root!=null){
            if(!isLeaf(root)) ans.add(root.val);

            if(root.left != null)
                root = root.left;
            else{
                root = root.right;
            }
        }
    }
    public List<Integer> boundary(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null) return ans;
				
        addLeftSide(root, ans);
        addLeaves(root, ans);
        addRightSide(root, ans);

        return ans;

    }


    // more concise
    class Solution {
        boolean isLeaf(TreeNode node) {
            return node.left == null && node.right == null;
        }
    
        void addLeftBoundary(TreeNode node, List<Integer> ans) {
            while (node != null) {
                if (!isLeaf(node)) ans.add(node.val);
                node = (node.left != null) ? node.left : node.right;
            }
        }
    
        void addRightBoundary(TreeNode node, List<Integer> ans) {
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            while (node != null) {
                if (!isLeaf(node)) stack.push(node.val);
                node = (node.right != null) ? node.right : node.left;
            }
            while (!stack.isEmpty()) ans.add(stack.pop());
        }
    
        void addLeaves(TreeNode node, List<Integer> ans) {
            if (node == null) return;
            if (isLeaf(node)) {
                ans.add(node.val);
                return;
            }
            addLeaves(node.left, ans);
            addLeaves(node.right, ans);
        }
    
        public List<Integer> boundary(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            if (root == null) return ans;
    
            if (!isLeaf(root)) ans.add(root.val);       // Add root if not leaf
            addLeftBoundary(root.left, ans);            // left boundary excluding root
            addLeaves(root, ans);                       // all leaves
            addRightBoundary(root.right, ans);          // right boundary excluding root, bottom-up
    
            return ans;
        }
    }
    

    
}

