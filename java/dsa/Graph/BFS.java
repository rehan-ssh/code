public class BFS {
    public List<Integer> bfsGraph(int v, List<List<Integer>> adj){
        List<Integer> bfs = new ArrayList<>();
        boolean [] vis = new boolean[v];
        Deque<Integer> q = new ArrayDeque<>();
        
        q.add(0);
        vis[0] = true;
        
        while(!q.isEmpty()){
            Integer node = q.poll();
            bfs.add(node);
            
            for(Integer it: adj.get(node)){
                if(vis[it] == false){
                    vis[it] = true;
                    q.add(it);
                }
            
            }
        }
    
        return bfs;
    
    }
}
