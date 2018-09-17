import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.IOException;

public class EdgeWeightedDigraph {

    private final int V; // nr of vertices
    private int E; // nr of edges
    private Bag<DirectedEdge>[] adj; // adjaceny list

    public EdgeWeightedDigraph(int V) {

	if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
	this.V = V;
	this.E = 0;
	adj = (Bag<DirectedEdge>[]) new Bag[V];

	for(int i =0; i < V; i++)
	    adj[i]= new Bag<DirectedEdge>();
    }

    // Build graph from System.in
    public EdgeWeightedDigraph() {

	Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // assume unicode utf-8
	this.V = scanner.nextInt(); // set up using the other constructor. Arg is nr of vertices
	this.E = scanner.nextInt(); // nr of Edges
        if (this.E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");

	adj = (Bag<DirectedEdge>[]) new Bag[V];
	for(int i =0; i < V; i++)
	    adj[i]= new Bag<DirectedEdge>();
	
	for (int i = 0; i < this.E; i++) {
	    int v = scanner.nextInt();
	    int w = scanner.nextInt();
	    double weight = scanner.nextDouble();
	    addEdge(new DirectedEdge(v,w,weight));
	}	
	
    }

    public int V() {return V; }
    public int E() {return E; }

    public void addEdge(DirectedEdge e) {
	int v = e.from();
	int w = e.to();
	validateVertex(v);
	validateVertex(w);
	adj[v].add(e);
	E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
	validateVertex(v);
	return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
	Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
	for (int v =0; v < V; v++) {
	    for (DirectedEdge e : adj(v)){
		bag.add(e);
	     }
	}
	return bag;

    }
     // throw an IllegalArgumentException unless {0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}
