import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.IOException;

public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V; // nr of vertices
    private int E; // nr of edges
    private Bag<Edge>[] adj; // adjaceny list

    public EdgeWeightedGraph(int V) {

	if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
	this.V = V;
	this.E = 0;
	adj = (Bag<Edge>[]) new Bag[V];

	for(int i =0; i < V; i++)
	    adj[i]= new Bag<Edge>();
    }

    // Build graph from System.in
    public EdgeWeightedGraph() {

	Scanner scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8"); // assume unicode utf-8
	this.V = scanner.nextInt(); // set up using the other constructor. Arg is nr of vertices
	this.E = scanner.nextInt(); // nr of Edges
        if (this.E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");

	adj = (Bag<Edge>[]) new Bag[V];
	for(int i =0; i < V; i++)
	    adj[i]= new Bag<Edge>();

	int nrOfEdges = this.E; // weird index error cant use this.E inside for loop.
	for (int i = 0; i < nrOfEdges; i++) {
	    int v = scanner.nextInt();
	    int w = scanner.nextInt();
	    double weight = scanner.nextDouble();
	    addEdge(new Edge(v,w,weight));
	}	
	
    }

    public int V() {return V; }
    public int E() {return E; }

    public void addEdge(Edge e) {
	int v = e.either();
	int w = e.other(v);
	validateVertex(v);
	validateVertex(w);
	adj[v].add(e);
	adj[w].add(e);
	E++;
    }

    public Iterable<Edge> adj(int v) {
	validateVertex(v);
	return adj[v];
    }

        
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Iterable<Edge> edges() {
	Bag<Edge> bag = new Bag<Edge>();
	for (int v =0; v < V; v++) {

	    for (Edge e : adj(v)) {
		
		if (e.other(v) > v)
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) throws Exception {

	EdgeWeightedGraph G = new EdgeWeightedGraph();
	System.out.println(G);
	    
    }
}
