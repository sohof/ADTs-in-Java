public class LazyPrimMST {
    // the lazy imp. leaves edges on the PQ, which it knows will never be used. The eager improves the space
    //complexity of the algorithm. See book for more info.

    private boolean[] marked; // Vertices on MST,A wayto signal which vertices are on the MST. Used by the algorithm. 
    private Queue<Edge> mst; // edges the comprimise the MST. This is what client usually wants to know.
    private MinPQ<Edge> pq;
    
    public LazyPrimMST(EdgeWeightedGraph G) {

	marked = new boolean[G.V()];
	mst = new Queue<Edge>();
	pq = new MinPQ<Edge>();  //  pq will rezize automatically

	visit(G, 0); // we assume G is connected.

	while (!pq.isEmpty()) {

	    Edge e = pq.delMin(); //repeatedly delete the min weight edge e = v -> w fro PQ

	    int v = e.either(); int w = e.other(v);
	    if (marked[v] && marked[w])  // ignore if both endpoints in MST
		continue;
	    mst.enqueue(e);  // add edge to MST
	    
	    if (!marked[v]) visit(G,v);  // we visit whichever vertex that was not on the tree
	    if (!marked[w]) visit(G,w);  // and put it on the tree and add edges incident to it
				
	    
	}
    }

    // puts arg vertex v on mst and add all edges incident to v on the pq 
    private void visit(EdgeWeightedGraph G, int v) {

	marked[v]=true; // add v to the tree. 
	for (Edge e : G.adj(v)) {

	    if (!marked[e.other(v)]) // for each e=v->w, add e to pq if w not already in T
		pq.insert(e);	    
	}
    }

    public Iterable<Edge> edges() {return mst; }

    public static void main(String[] args) throws Exception {

	EdgeWeightedGraph G = new EdgeWeightedGraph();

	LazyPrimMST mst = new LazyPrimMST(G);

	for(Edge e : mst.edges())
	    System.out.println(e);
    }

}
