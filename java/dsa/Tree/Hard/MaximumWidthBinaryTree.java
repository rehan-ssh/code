package com.github.dsa.Tree.Hard;

import java.util.ArrayDeque;
import java.util.Deque;

import com.github.dsa.Tree.TreeNode;

public class MaximumWidthBinaryTree {
    
class Solution {

    class QNode{
        TreeNode t;
        int index;

        QNode(TreeNode t, int index){
            this.t = t; 
            this.index = index;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        
        Deque<QNode> que = new ArrayDeque<>();
        int max = Integer.MIN_VALUE;
        que.offer(new QNode(root, 1));

        while(!que.isEmpty()){
            int size = que.size();
            QNode first = new QNode(null,0), last = new QNode(null,0);

            for(int i = 0; i < size; i++){
                QNode curr = que.poll();
                if(i==0) first = curr;
                if(i==size-1) last = curr;

                int index = curr.index;
                TreeNode t = curr.t;

                if(t.left!=null) que.offer(new QNode(t.left, (index-1)*2+1));
                if(t.right!=null) que.offer(new QNode(t.right, (index-1)*2+2));

            }

            max = Math.max(max, last.index - first.index + 1);
        }

        return max;
    }
}
}
