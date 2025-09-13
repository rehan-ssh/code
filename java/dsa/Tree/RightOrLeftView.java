package com.github.dsa.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RightOrLeftView {
    class SolutionR {

        // do a Root R L traversal
        // if we are at new level, we add the node to our ans [ie whenever we come first
        // time we add]

        Map<Integer, Integer> ds;

        void f(TreeNode node, int level) {
            if (node == null)
                return;
            ds.putIfAbsent(level, node.val);
            if (node.right != null)
                f(node.right, level + 1);
            if (node.left != null)
                f(node.left, level + 1);
        }

        public List<Integer> rightSideView(TreeNode root) {
            ds = new TreeMap<>();
            f(root, 0);
            return new ArrayList<>(ds.values());
        }
    }

    class SolutionL {
        Map<Integer, Integer> ds;

        void f(TreeNode node, int level) {
            if (node == null)
                return;
            ds.putIfAbsent(level, node.val);
            if (node.left != null)
                f(node.left, level + 1);
            if (node.right != null)
                f(node.right, level + 1);
        }

        public List<Integer> rightSideView(TreeNode root) {
            ds = new TreeMap<>();
            f(root, 0);
            return new ArrayList<>(ds.values());
        }

    }
}
