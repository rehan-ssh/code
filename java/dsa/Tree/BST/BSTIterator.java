public class BSTIterator{
	private Stack<TreeNode> stack = new Stack<TreeNode>();
	
	public BSTIterator(TreeNode root){
		pushAll(root);
	}
	
	void pushAll(TreeNode root){
		while(root!=null){
			stack.push(root);
			
			root = root.left;
		}
	}
	
	
	public int next(){
		if(stack.isEmpty()) return -1;
		
		TreeNode node = stack.pop();
		if(node.right!=null){
			TreeNode curr = node.right;
			while(curr!=null){
				stack.push(curr);
				curr = curr.left;
			}
		
		}
		return node.val;
	
	}
	
	public boolean hasNext(){
		return !stack.isEmpty();
	}



}