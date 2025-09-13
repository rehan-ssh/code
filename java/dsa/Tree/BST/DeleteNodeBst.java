public class DeleteNodeBst {
    class Solution {
        public TreeNode deleteNode(TreeNode root, int key) {
            if(root==null) return null;
    
            // if root is the node to be deleted
            if(root.val == key){
                if(root.left==null) return root.right;
                
                TreeNode curr = root.left;
    
                if(curr.right==null){
                    root.val = curr.val;
                    root.left = curr.left;
                }
                else{
                    TreeNode prev = root;
                    while(curr.right!=null){
                        prev = curr;
                        curr = curr.right;
                    }
                    prev.right = curr.left;
                    root.val = curr.val;
                }
    
            }
            else{
                if(root.val>key){
                    root.left = deleteNode(root.left, key);
                }
                else{
                    root.right = deleteNode(root.right, key);
                }
            }
    
            return root;
    
            
        }
    }
}
