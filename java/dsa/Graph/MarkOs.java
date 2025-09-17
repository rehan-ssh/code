public class MarkOs {
    void dfs(int r, int c, int[][] vis, char[][] matrix){
        if(vis[r][c] == 1) return;
        
        vis[r][c] = 1;
        
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};
        
        for(int i = 0; i < 4; i++){
            int row = r+ dRow[i];
            int col = c + dCol[i];
            
            if(row>=0&&col>=0&&row<matrix.length&&col<matrix[0].length&&vis[row][col] == 0&&matrix[row][col] == 'O'){
                dfs(row, col, vis, matrix);			
                
            }
        
        }
    
    
    }
    
    char[][] markOs(char[][] matrix){
        int m = matrix.length;
        int n = matix[0].length;
        int vis[][] = new int[m][n];
        
        char [][] ans = new char[m][n];
    
        // go over the boundarys and mark all connected O's in vis
        
        for(int i = 0; i < m; i++){
            if(matrix[i][0] == 'O'){
                dfs(i, 0, vis, matrix);
            }
            if(matrix[i][n-1] == 'O'){
                dfs(i, n-1, vis, matrix);
            }
        }
        
        for(int i = 0; i < n; i++){
            if(matrix[0][i] == 'O'){
                dfs(0, i, vis, matrix);
            }
            if(matrix[m-1][i] == 'O'){
                dfs(m-1, i, vis, matrix);
            }
        }
    
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] == 'X' || vis[i][j] == 0)  ans[i][j] = 'X';
                else{
                    ans[i][j] = 'O';
                }
            }
        }
        
    return ans;
    
    }
    
    
}
