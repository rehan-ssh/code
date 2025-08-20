package com.github.dsa.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.TreeMap;



public class TopViewAndBottomView {

    class SolutionT {
        public List<Integer> topView(TreeNode root) {
            if (root == null) return new ArrayList<>();
            
            TreeMap<Integer, Integer> ds = new TreeMap<>();
            Deque<Pair<TreeNode, Integer>> que = new ArrayDeque<>();
            que.offer(new Pair<>(root, 0));
    
            while (!que.isEmpty()) {
                Pair<TreeNode, Integer> p = que.poll();
                TreeNode node = p.getKey();
                int level = p.getValue();
    
                ds.putIfAbsent(level, node.val);
    
                if (node.left != null) que.offer(new Pair<>(node.left, level - 1));
                if (node.right != null) que.offer(new Pair<>(node.right, level + 1));
            }
    
            return new ArrayList<>(ds.values());
        }
    }
    // only difference is put istead of putIfAbsent
    class SolutionB {
        public List<Integer> topView(TreeNode root) {
            if (root == null) return new ArrayList<>();
            
            TreeMap<Integer, Integer> ds = new TreeMap<>();
            Deque<Pair<TreeNode, Integer>> que = new ArrayDeque<>();
            que.offer(new Pair<>(root, 0));
    
            while (!que.isEmpty()) {
                Pair<TreeNode, Integer> p = que.poll();
                TreeNode node = p.getKey();
                int level = p.getValue();
    
                ds.put(level, node.val);
    
                if (node.left != null) que.offer(new Pair<>(node.left, level - 1));
                if (node.right != null) que.offer(new Pair<>(node.right, level + 1));
            }
    
            return new ArrayList<>(ds.values());
        }
    }
    
    
}

class Pair<U, V> {
    U first;
    V second;

    Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }


    U getKey(){
        return first;
    }

    V getValue(){
        return second;
    }
}
