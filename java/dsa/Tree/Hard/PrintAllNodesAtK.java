package com.github.dsa.Tree.Hard;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.dsa.Tree.TreeNode;

public class PrintAllNodesAtK {

    // we map parents, then do a bfs like in graphs with left, right, and parent

    class Solution {
        Map<TreeNode, TreeNode> parentMap;

        void markParents(TreeNode root) {
            parentMap = new HashMap<>();
            Deque<TreeNode> que = new ArrayDeque<>();
            que.offer(root);
            while (!que.isEmpty()) {
                TreeNode curr = que.poll();
                if (curr.left != null) {
                    que.offer(curr.left);
                    parentMap.put(curr.left, curr);
                }
                if (curr.right != null) {
                    que.offer(curr.right);
                    parentMap.put(curr.right, curr);
                }
            }
        }

        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {

            markParents(root);
            Set<TreeNode> visited = new HashSet<>();
            Deque<TreeNode> que = new ArrayDeque<>();

            que.offer(target);
            int level = 0;
            while (!que.isEmpty()) {
                int size = que.size();
                if (level == k)
                    break;
                level++;
                for (int i = 0; i < size; i++) {
                    TreeNode curr = que.poll();
                    visited.add(curr);
                    if (curr.left != null && !visited.contains(curr.left)) {
                        que.offer(curr.left);
                    }
                    if (curr.right != null && !visited.contains(curr.right)) {
                        que.offer(curr.right);
                    }
                    if (parentMap.containsKey(curr) && !visited.contains(parentMap.get(curr))) {
                        que.offer(parentMap.get(curr));
                    }
                }

            }

            return que.stream().map(t -> t.val).collect(Collectors.toList());

        }
    }

    
}
