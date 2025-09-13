package com.github.dsa.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class VerticalOrderTraversal {

    class Solution {

        class QNode {
            TreeNode t;
            int hlvl;
            int vlvl;

            QNode(TreeNode t, int hlvl, int vlvl) {
                this.t = t;
                this.hlvl = hlvl;
                this.vlvl = vlvl;
            }

        }

        public List<List<Integer>> verticalTraversal(TreeNode root) {

            // vlvl - list of pq's one for each horizontal level [each pq has nodes for that
            // vlvl and hlvl]
            TreeMap<Integer, List<PriorityQueue<Integer>>> ds = new TreeMap<>();
            // this list could have been treemap for with hlvl as key and it would have sufficed
            Deque<QNode> que = new ArrayDeque<>();

            que.offer(new QNode(root, 0, 0));

            while (!que.isEmpty()) {
                int size = que.size();
                for (int i = 0; i < size; i++) {
                    QNode n = que.poll();
                    List<PriorityQueue<Integer>> alist = ds.getOrDefault(n.vlvl, new ArrayList<>());
                    // ensure list has enough rows for this hlvl
                    while (alist.size() <= n.hlvl) {
                        alist.add(new PriorityQueue<>());
                    }

                    // add current value
                    alist.get(n.hlvl).add(n.t.val);

                    ds.put(n.vlvl, alist);

                    if (n.t.left != null)
                        que.offer(new QNode(n.t.left, n.hlvl + 1, n.vlvl - 1));
                    if (n.t.right != null)
                        que.offer(new QNode(n.t.right, n.hlvl + 1, n.vlvl + 1));

                }

            }
            List<List<Integer>> ans = new ArrayList<>();

            for (Map.Entry<Integer, List<PriorityQueue<Integer>>> entry : ds.entrySet()) {
                List<Integer> vlist = new ArrayList<>();
                List<PriorityQueue<Integer>> list = entry.getValue();
                for (PriorityQueue<Integer> pq : list) {
                    while (!pq.isEmpty()) {
                        vlist.add(pq.poll());
                    }
                }
                ans.add(vlist);

            }
            return ans;

        }
    }

}
