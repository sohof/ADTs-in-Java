public class KruskalMST {

    private Queue<Edge> mst = new Queue<Edge>();

    public KruskalMST(EdgeWeightedGraph G)
    {
	MinPQ<Edge> pq = new MinPQ<Edge>();

	for (Edge e : G.edges())
	    pq.insert(e);

	UF uf = new UF(G.V());
	while (!pq.isEmpty() && mst.size() < (G.V()-1))
	{    
	    Edge e = pq.delMin();
	    int v = e.either(), w = e.other(v);
	    if(!uf.connected(v,w)) // add edge to mst as long as no cycle is created
	    {
		uf.union(v,w);
		mst.enqueue(e);
	    }
	}
    }

    public Iterable<Edge> edges() { return mst; }

    public static void main(String[] args) throws Exception {

	EdgeWeightedGraph G = new EdgeWeightedGraph();

	KruskalMST mst = new KruskalMST(G);

	for(Edge e : mst.edges())
	    System.out.println(e);
    }

}
