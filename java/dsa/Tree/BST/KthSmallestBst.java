public class KthSmallestBst {
    class Solution {
        int count = 0;
        int kthSmallest = -1;
    
        void inorder(TreeNode root, int k){
            if(root == null || count>=k) return;
    
            inorder(root.left, k);
            count++;
            if(count == k) kthSmallest = root.val;
            inorder(root.right, k);
        }
    
    
    
        public int kthSmallest(TreeNode root, int k) {
            inorder(root, k);
            return kthSmallest;
        }
    }

    class SolutionMorrisTraversal{
        public int kthSmallest(TreeNode root, int k) {

            TreeNode curr = root;
            int count = 0;
            while(curr!=null){
                if(curr.left == null){
                    count++;
                    if(count == k){ return curr.val;}
                    curr = curr.right;
                }
                else{
                    TreeNode prev = curr.left;
                    while(prev.right!=null && prev.right!=curr){
                        prev = prev.right;
                    }
                    if(prev.right == null){
                        prev.right = curr;
                        curr = curr.left;
                    }
                    else{
                        prev.right = null;
                        count++;
                        if(count == k) return curr.val;
                        curr = curr.right;
                    }
                }

            }
            return -1;
        }
    }
}
