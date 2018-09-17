import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

public class Digraph {
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";
    private Scanner scanner;
    private final int V;
    private int E;
    private Bag<Integer>[] adj;


     public Digraph() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        this.V = scanner.nextInt(); //Non static final variable must assigned a value in a constructor or 
        // or with declaration.  
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        constructGraph();       

     }
    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }


    public Digraph(File file) {
         try {
            scanner = new Scanner(file, CHARSET_NAME);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + file);
        }
        this.V = scanner.nextInt(); // read nr of vertices. Must do it here since V is declared final! 
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        constructGraph();
    }


    public Digraph(Digraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    private void constructGraph() {
	
        this.E = scanner.nextInt(); // read nr of edges. 
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");

        adj = (Bag<Integer>[]) new Bag[V]; // Create array of lists.
        for (int v = 0; v < V; v++) {       // Initialize all lists
            adj[v] = new Bag<Integer>();    //   to empty.
        }

        int nrEdges = E(); 
        for (int i = 0; i < nrEdges; i++) { 
                int v = scanner.nextInt();            // Read a vertex,
                int w = scanner.nextInt();          // read another vertex,
            addEdge(v, w);                    // and add edge connecting them.

        }
    }   

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }


    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }


    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
    }


    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }


    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);
            }
        }
        return R;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {

        File file = new File(args[0]);
        Digraph G = new Digraph(file);
        System.out.println(G);
    }

}
