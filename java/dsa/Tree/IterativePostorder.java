package com.github.dsa.Tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class IterativePostorder {

    class Node {
        int data;
        Node left;
        Node right;
    }

    void postorderIterative(Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        Node lastVisited = null;
        while (true) {

            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            if (stack.isEmpty())
                break;

            root = stack.pop();
            if (root.right != null && root.right != lastVisited) {
                stack.push(root);
                root = root.right;
            } else {
                System.out.println(root.data);
                lastVisited = root;
                root = null;
            }
        }
    }

    void preorderIterative(Node root) {
        Deque<Node> stack = new ArrayDeque<>();

        while (true) {
            if (root != null) {
                System.out.println(root.data);
                stack.push(root);
                root = root.left;
            }

            if (stack.isEmpty())
                break;
            root = stack.pop();
            root = root.left;
        }

    }

    void inorderIterative(Node root) {
        Deque<Node> stack = new ArrayDeque<>();

        while (true) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                if (stack.isEmpty())
                    break;
                root = stack.pop();
                System.out.println(root.data);
                root = root.right;
            }
        }
    }

}