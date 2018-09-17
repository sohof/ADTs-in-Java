//Weighted quick-union by rank with path compression by halving.

import java.util.Scanner;

public class UF {
    
    private int [] id;     // id[i] = parent of i
    private byte[] rank;  // rank[i] = rank of subtree rooted at i (cannot be more than 31)
    private int count;    // number of components

     /**
     * Initializes an empty union-find data structure with <tt>N</tt>
     * isolated components <tt>0</tt> through <tt>N-1</tt>
     * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
     * @param N the number of sites
     */
    public UF(int N) {
	if (N < 0) throw new IllegalArgumentException();
	count = N;
	id = new int[N];
	rank = new byte[N];

	for(int i = 0; i < N; i++ ){
	    id[i] = i;
	    rank[i] = 0;
	}	    
    }

    // Returns the number of components.
    public int count() { return count; }

     
    //Are the two sites p and q  in the same component
    public boolean connected(int p, int q) {

	return find(p) == find(q);
    }

    
    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     * @param p the integer representing one object
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws java.lang.IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
     */
    public int find(int p) {
	if( p < 0 || p >= id.length) throw new IndexOutOfBoundsException();

	while(p != id[p]){
	    id[p] = id[id[p]];    // path compression by halving
	    p = id[p];
	}
	return p;
    }
    

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[i] < rank[j]) id[i] = j;
        else if (rank[i] > rank[j]) id[j] = i;
        else {
            id[j] = i;
            rank[i]++;
        }
        count--;
    }

    public static void main(String [] args){

	Scanner scan = new Scanner(System.in);
	int N = scan.nextInt();

	UF uf = new UF(N);

	while(scan.hasNext()) {

	    int p = scan.nextInt();
	    int q = scan.nextInt();

	    if(uf.connected(p, q)) continue;
	    uf.union(p, q);
	    System.out.println(p + " " + q);
	    
	}
	System.out.println(uf.count() + " components");
	
    }
    
}
