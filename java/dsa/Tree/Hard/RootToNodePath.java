import java.util.ArrayList;
import java.util.List;

import com.github.dsa.Tree.TreeNode;

class RootToNodePath {
    class Solution {
        public boolean getPath(TreeNode root, List<Integer> path, int x) {
            if (root == null)
                return false;
            path.add(root.val);
            if (root.val == x) {
                return true;
            }

            if (getPath(root.left, path, x)) {
                return true;
            }
            if (getPath(root.right, path, x)) {
                return true;
            }
            path.remove(path.size() - 1);
            return false;
        }

        public List<Integer> solve(TreeNode A, int B) {
            List<Integer> ans = new ArrayList<>();
            getPath(A, ans, B);
            return ans;
        }
    }

}
