class Solution {

    private static class NodeValue {
        int minNode;
        int maxNode;
        int maxSize;
        boolean isBST;
        NodeValue(int minNode, int maxNode, int maxSize, boolean isBST){
            this.minNode = minNode;
            this.maxNode = maxNode;
            this.maxSize = maxSize;
            this.isBST = isBST;
        }
    }

    public int largestBSTSubtree(TreeNode root) {
        return largestBSTSubtreeHelper(root).maxSize;
    }

    private NodeValue largestBSTSubtreeHelper(TreeNode node) {
        if (node == null) {
            // For null, treat as BST of size = 0
            return new NodeValue(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, true);
        }

        NodeValue left = largestBSTSubtreeHelper(node.left);
        NodeValue right = largestBSTSubtreeHelper(node.right);

        // Check if current subtree rooted at `node` is BST
        if (left.isBST && right.isBST 
            && node.val > left.maxNode 
            && node.val < right.minNode) {
            
            // It's a BST
            int currentSize = left.maxSize + right.maxSize + 1;
            int currentMin = Math.min(left.minNode, node.val);
            int currentMax = Math.max(right.maxNode, node.val);
            return new NodeValue(currentMin, currentMax, currentSize, true);
        }

        // Not a BST, return the larger of left or right subtree
        // We mark this subtree as not BST, so parent cannot count it fully
        int bestSize = Math.max(left.maxSize, right.maxSize);
        return new NodeValue(Integer.MIN_VALUE, Integer.MAX_VALUE, bestSize, false);
    }
}
