class Solution {
    boolean[] vis;
    List<Integer> ans;
    List<List<Integer>> adj;

    void dfs(int node) {
        vis[node] = true;
        ans.add(node);
        for (Integer nei : adj.get(node)) {
            if (!vis[nei]) {
                dfs(nei);
            }
        }
    }

    public List<Integer> dfsOfGraph(int v, List<List<Integer>> adj) {
        vis = new boolean[v]; // nodes are 0 to v-1
        ans = new ArrayList<>();
        this.adj = adj;

        for (int i = 0; i < v; i++) {
            if (!vis[i]) {
                dfs(i);
            }
        }

        return ans;
    }
}
