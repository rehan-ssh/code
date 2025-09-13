import com.github.dsa.Tree.TreeNode;


public class BST {
    boolean bstSearch(TreeNode root, int x) {
        if (root == null) return false;
    
        if (x == root.val) return true;
        else if (x < root.val) return bstSearch(root.left, x);
        else return bstSearch(root.right, x);
    }

    TreeNode bstNodeSearch(TreeNode root, int x) {
        while(root != null && root.val != x) {
            root = x < root.val ? root.left : root.right;
        }
        return root;
    }
    
    
    
}
