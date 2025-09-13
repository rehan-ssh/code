public class FloorInBst {
    
    bstFloor(TreeNode root, int x){
        int floor = -1;
        
        while(root!=null && root.val!=x){
            if(root.val<x) floor = root.val;
            root = root.val<x? root.right: root.left;
        
        }
        
        if(root!=null) return root.val;
        return floor;
    
    
    }
}
