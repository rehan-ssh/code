public class TwoSum4 {
    public class BSTIterator{
        private Stack<TreeNode> stack = new Stack<TreeNode>();
        boolean isReverse;
        
        public BSTIterator(TreeNode root, boolean isReverse){
            this.isReverse = isReverse;
            pushAll(root);
        }
        
        void pushAll(TreeNode root){
            while(root!=null){
                stack.push(root);
                if(!isReverse) root = root.left;
                else root = root.right;
            }
            
        }
        
        
        public int next(){
            if(stack.isEmpty()) return -1;
            
            TreeNode node = stack.pop();
            if(isReverse){
                if(node.left!=null){
                  TreeNode curr = node.left;
                    while(curr!=null){
                        stack.push(curr);
                        curr = curr.right;
                    }
                }
            }
            else{
                if(node.right!=null){
                    TreeNode curr = node.right;
                    while(curr!=null){
                        stack.push(curr);
                        curr = curr.left;
                    }
                
                }
            }
            return node.val;
        
        }
        
        public boolean hasNext(){
            return !stack.isEmpty();
        }
    
    }
    
    class TwoSum {
        boolean twoSum(TreeNode root, int k){
            if(root == null) return false;
            BSTIterator forward = new BSTIterator(root, false);
            BSTIterator backward = new BSTIterator(root, true);
            int s1 = forward.next();
            int s2 = backward.next();
            while(s1<s2){	
                if(s1+s2==k) return true;
                else if(s1+s2>k){
                    if(backward.hasNext())s2 = backward.next();
                    else break;
                }
                else{
                    if(forward.hasNext())s1 = forward.next();
                    else break;
                }
            
            }
            return false;
        }
    }
}
