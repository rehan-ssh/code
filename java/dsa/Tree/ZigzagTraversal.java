package com.github.dsa.Tree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

public class ZigzagTraversal {

    class Solution {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null)
                return new LinkedList<>();
            ArrayDeque<TreeNode> que = new ArrayDeque<>();
            que.offer(root);
            boolean flip = true;
            List<List<Integer>> ans = new LinkedList<>();

            while (!que.isEmpty()) {
                int size = que.size();
                List<Integer> curr = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    root = que.poll();
                    if (flip)
                        curr.addLast(root.val);
                    else
                        curr.addFirst(root.val);
                    if (root.left != null)
                        que.offer(root.left);
                    if (root.right != null)
                        que.offer(root.right);
                }
                flip = !flip;
                ans.add(curr);
            }

            return ans;
        }
    }

}
