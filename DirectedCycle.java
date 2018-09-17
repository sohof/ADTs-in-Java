public class DirectedCycle {
    private boolean[] marked;     // marked[v] = has vertex v been marked?
    private int edgeTo [];      // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    //directed cycle (null if no such cycle exists)
    
    public DirectedCycle(Digraph G) {
	marked  = new boolean[G.V()];
	onStack = new boolean[G.V()];
	edgeTo  = new int[G.V()];

	for (int i = 0; i < G.V(); i++) {
	    if (!marked[i] && cycle == null) dfs(G,i);	    
	}
    }

    private void dfs(Digraph G, int v) {
	onStack[v]= true;
	marked[v]= true;
       
	for (int w : G.adj(v)) {
	    if (cycle != null) return;
	
	    else if (!marked[w]){
		edgeTo[w]=v;
		dfs(G,w);
	    }

	    else if (onStack[w]=true) {
		cycle = new Stack<Integer>();
		for (int x = v; x != w; x=edgeTo[x]) {
			cycle.push(x);
		    }
		cycle.push(w);
		cycle.push(v);
		       
	    }
	}
	onStack[v]=false;
    }
    
    public boolean hasCycle() { return cycle!=null; }
	
    public Iterable<Integer> cycle() { return cycle; }
    
    public static void main(String[] args) throws Exception {

        Digraph G = new Digraph();
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

        else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
	
    }
    
}
