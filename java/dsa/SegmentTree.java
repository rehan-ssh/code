package com.github.dsa;

public class SegmentTree {
    int[] tree;
    int n;

    public SegmentTree(int[] arr) {
        n = arr.length;
        /*
The formula comes from the fact that a segment tree is a full binary tree. We first
pad the array to the next power of 2, say N = 2^ceil (log2 (n) ) . A full binary tree
with N leaves has 2N - 1 nodes, including internal and leaf nodes. Substituting
N gives us 2 x 2^ceil (log2 (n)) - 1, which is the maximum number of nodes
needed. Since 2^ceil (log2(n)) ≤ 2n, we use 4n as a safe upper bound for
space.
         */
        // because a 10 size tree will require 16 spaces i.e power of 2
        tree = new int[4 * n]; // Safe size to hold all segments
        build(arr, 0, 0, n - 1);
    }

    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start]; // leaf node
        } else {
            int mid = start + (end - start) / 2;
            build(arr, 2 * node + 1, start, mid); // left
            build(arr, 2 * node + 2, mid + 1, end); // right
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    public int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }

    private int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l)
            return 0; // no overlap
        if (l <= start && end <= r)
            return tree[node]; // full overlap

        int mid = start + (end - start) / 2;
        int left = query(2 * node + 1, start, mid, l, r);
        int right = query(2 * node + 2, mid + 1, end, l, r);

        return left + right;
        // at any level only max two nodes will be partial as range is continuous
        // other nodes will be completely in or out
        // hence complexity of this query function is O(logn)
    }

    public void update(int index, int value) {
        update(0, 0, n - 1, index, value);
    }

    public void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid)
                update(2 * node + 1, start, mid, idx, val);
            else
                update(2 * node + 2, mid + 1, end, idx, val);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1, 3, 5, 7, 9, 11 };
        SegmentTree segTree = new SegmentTree(arr);

        System.out.println(segTree.query(1, 3)); // Output: 15 (3+5+7)

        segTree.update(1, 10); // Change arr[1] = 10

        System.out.println(segTree.query(1, 3)); // Output: 22 (10+5+7)
    }

}
