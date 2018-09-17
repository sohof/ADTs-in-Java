public class DFSPaths {

    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    public DFSPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v. Solves single source connectivity/reachability
    private void dfs(Graph G, int v){
	marked[v]=true;
      	for(int w : G.adj(v)) {
	    if (!marked[w]) {
		    edgeTo[w] = v;
		    dfs(G, w);
		}
	}
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
	
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
	
        path.push(s);
        return path;
    }

    public static void main(String[] args) {

        Graph G = new Graph();  // from stdin

        int s = 0;// Integer.parseInt(args[1]);
        DFSPaths dfs = new DFSPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.print(s + " to "  + v + ": ");
                for (int x : dfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else        System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.println(s + " to " + v +  ": not connected");
            }

        }
    }   
}
