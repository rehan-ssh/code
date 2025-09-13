public class CeilInBst {
    int ceilInBst(TreeNode root, int x) {
        if (root == null) return -1;

        if (root.val == x) return root.val;
        else if (root.val > x) return ceilInBst(root.left, x);
        else return ceilInBst(root.right, x);
    }


    int bstCeil(TreeNode root, int x){
        int ceil = -1;
        while(root!= null && root.val != x){
            if(root.val>x) ceil = root.val;
            root = root.val<x? root.right: root.left;
            
        }
        
        if(root!=null) return root.val;
        return ceil;
    
    }

}
