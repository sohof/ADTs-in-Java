public class DijkstraSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<DirectedEdge> pq;
    private final double ZERO = 0.0;
    
    public DijkstraSP(EdgeWeightedDigraph G, int s) {

	int nrVertices = G.V();
	edgeTo = new DirectedEdge[nrVertices];
	distTo = new Double[nrVertices];
	pq = new IndexMinPQ<Double>(nrVertices); 
	

	for (int i = 0; i < nrVertices; i++) {
	    distTo[i] = Double.Positive_Infinity;
	}
	distTo[s]= ZERO; // Distance from source to itself is zero. 

	pq.insert(s,ZERO);
	while (!pq.isEmpty())
	    {
		int v = pq.delMin();
		for (DirectedEdge e : G.adj(v))
		    relax(e);
		
	    }
		
    }

    public void relax(DirectedEdge e) {

	int v = e.from(); int w = e.to();

	if (distTo[w] > distTo[v] + e.weight())
        {
	    edgeTo[w]= e;
	    distTo[w] = distTo[v] + e.weight();
	    
	    if (pq.contains(w))
		pq.decreaseKey(w, distTo[w]);
	    else
		pq.insert(w, distTo[w]);
	   
	}
    }

    public double distTo(int v) { return distTo[v]; }
    public boolean hasPathTo(int v) {return distTo[v] < Double.Positive_Infinity ;}

    public void relax(EdgeWeightedDigraph G, int v) {
	
	for (DirectedEdge e : G.adj(v)) {
	    int w = e.to();
	    
	    if(distTo[w] > distTo[v] + e.weight())
	    {
		    edgeTo[w] = e;
		    distTo[w] = distTo[v] + e.weight();
	    }
	}
    }


    public Iterable<DirectedEdge> pathTo(v) {
	if (!hasPathTo(v)) return null;
	
	Stack<DirectedEdge> path = new Stack<DirectedEdge>();
	
	
	for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
	    path.push(e);
	}
	return path;
    }

}
