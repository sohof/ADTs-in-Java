/*
* Heap Ordering = A binary tree is heap ordered if the key in each node is larger
* than or equal to  the keys in that node children (if any). 
* We represent heap of size N in private array pq[] of length N+1, with
* pq[0] unused and the heap in pq[1] through pq[N]. The parent of node in position k is
* in position floor (k/2) and conversely, its two children in positions 2k and 2k+1.
* It is assumed that the keys are immutable and not changed by any client since that
* break the heap ordering. Also automatic array resizing is not addes yet.
*Author Sohof Jan 22, 2017
*/


public class MaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN) {pq = (Key[]) new Comparable[maxN + 1]; }
    
    public boolean isEmpty() {return N == 0; }
    public int size() {return N;}

    public void insert(Key v){
	assert N < (pq.length+1);
	pq[++N] = v;
	swim(N);
    }

    public Key delMax(){

	assert (N>0);
	Key max = pq[1];   // Get max from ti
	exch(1, N--); //exchange with last item, then decrement nr of items
	pq[N+1] = null;  // Avoid loitering
	sink(1);
	return max; 
    }

    // Bottom-up reheapify 
    private void swim(int k) {

	// k/2 is the parent of k
	while(k > 1 && less(k/2, k)) {   

	    exch(k/2, k); // parent was less than k, so exchange
	    k = k/2; // set k to parent 
	}
    }

    // Top-down reahipfy
    private void sink(int k) {
	
	while(2*k <= N) {

	    int j = 2*k;
	    if (j < N && less(j,j+1)) j++;
	    //make sure in bounds and compare children of k. Adjust j so it is the bigger child
	    
	    if (!less(k,j)) break; // if parent no longer less than child j, then break. 
	    exch(k, j); // parent k was less than j, so exchange
	    k = j; // recheck condition downwards for new k 
	}
    }

    // helper function to compare keys/nodes
    private boolean less(int i, int j) {

	return pq[i].compareTo(pq[j]) < 0 ; }

    // Helper function to exchange to keys/nodes
    private void exch(int i, int j) {

	Key t = pq[i]; pq[i] = pq[j]; pq[j] = t;  }

}
