public class NumberOfProvinces {
    //https://leetcode.com/problems/number-of-provinces/
    class Solution {


        int[][] adjMatrix;
        int vis[];
        int cnt;
    
        void dfs(int node){
    
            vis[node] = 1;
    
            for(int i = 0; i < adjMatrix.length; i++){
                if(adjMatrix[node][i] == 1){
                    if(vis[i]==0){
                        dfs(i);
                    }
                }
            }
        }
    
    
        public int findCircleNum(int[][] isConnected) {
            adjMatrix = isConnected;
            int v = isConnected.length;
            vis = new int[v];
            cnt = 0;
            for(int i = 0; i < v; i++){
                if(vis[i] == 0){
                    cnt++;
                    dfs(i);
                }
            }
    
    
            return cnt;
            
        }
    }
}
