public class DetectCycleDFS {
    public boolean checkForCycle(int src, List<List<Integer>> adj, boolean[] vis){
        vis[src] = true;
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(src,-1));
        
        while(!q.isEmpty()){
            int node = q.peek().first;
            int parent = q.peek().second;
            q.poll();
            
            for(int v: adj.get(node)){
                if(!vis[v]){
                    vis[v] = true;
                    q.offer(new Pair(v, node));
                }
                else if(parent != v){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public boolean isCycle(int V, List<List<Integer>> adj){
        boolean vis[] = new boolean[V];
        
        for(int i =0; i < V; i++){
            if(vis[i] == false){
                if(vis[i] == false && checkForCycle(i, adj, vis))
                    return true;
            }
        }
        return false;
    
    
    }
}
