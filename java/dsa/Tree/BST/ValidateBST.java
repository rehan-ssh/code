public class ValidateBST {
    boolean validateBST(TreeNode root, long min, long max){
        if(root == null) return true;
        
        if(root.val<=min || root.val>=max) return false;
        
        return validateBST(root.left, min, root.val) &&
        validateBST(root.right, root.val, max);
    }
    public boolean isValidBST(TreeNode root) {
        return validateBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
}
