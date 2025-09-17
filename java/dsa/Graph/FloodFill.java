public class FloodFill {
    class Solution {


        void dfs(int[][] image, int sr, int sc, int color, int oldColor){
    
            if(sr<0 || sc<0 || sr>=image.length || sc>=image[0].length || image[sr][sc]!=oldColor){
                return;
            }
            image[sr][sc] = color;
    
            dfs(image, sr+1, sc, color, oldColor);
            dfs(image, sr-1, sc, color, oldColor);
            dfs(image, sr, sc+1, color, oldColor);
            dfs(image, sr, sc-1, color, oldColor);
    
        }
        
        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            
            int oldColor = image[sr][sc];
            int newColor = color;
            if(oldColor == newColor) return image;
    
            dfs(image, sr, sc, color, oldColor);
    
            return image;
        }
    }
}
