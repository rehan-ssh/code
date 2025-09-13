import com.github.dsa.Tree.TreeNode;

public class InorderPredecessorAndSuccessor {
    public class Solution1 {
        public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
            TreeNode successor = null;
    
            while (root != null) {
                if (p.val < root.val) {
                    successor = root;
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
    
            return successor;
        }
    }
    
    public class Solution2 {
        public TreeNode inorderPredecessor(TreeNode root, TreeNode p) {
            TreeNode predecessor = null;
    
            while (root != null) {
                if (p.val <= root.val) {
                    root = root.left;
                } else {
                        predecessor = root;
                    root = root.right;
                }
            }
    
            return predecessor;
        }
    }
    
}
