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
import java.util.NoSuchElementException;
public class MinPQ<Key extends Comparable<Key>> {

    private Key[] pq;  // store items at indices 1 to n
    private int N;     // number of items on priority queue
    
    public MinPQ(int initCapacity) {
	pq = (Key[]) new Comparable[initCapacity + 1];
	N =0;
    }

    public MinPQ() { this(1); } // initialize empty queue

    
    
    public boolean isEmpty() {return N == 0; }
    public int size() {return N;}

    public void insert(Key v) {
	// double size of array if necessary
        if (N == pq.length - 1) resize(2 * pq.length);
	
	assert N < (pq.length+1);
	pq[++N] = v;
	swim(N);
    }

    public Key delMin() {

	assert (N>0);
	Key min = pq[1];   // Get max from ti
	exch(1, N--); //exchange with last item, then decrement nr of items
	pq[N+1] = null;  // Avoid loitering
	sink(1);
	return min; 
    }
    
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // Bottom-up reheapify 
    private void swim(int k) {

	// k/2 is the parent of k
	while(k > 1 && greater(k/2, k)) {   

	    exch(k/2, k); // parent was greater than k, so exchange
	    k = k/2; // set k to parent 
	}
    }

    // Top-down reahipfy
    private void sink(int k) {
	
	while(2*k <= N) {

	    int j = 2*k;
	    if (j < N && greater(j,j+1)) j++;
	    if (!greater(k,j)) break; // if parent no longer greater than child j, then break. 
	    exch(k, j); // parent k was greater than j, so exchange
	    k = j; // recheck condition downwards for new k 
	}
    }

    // helper function to compare keys/nodes
    private boolean greater(int i, int j) {

	return pq[i].compareTo(pq[j]) > 0 ;
    }

    // Helper function to exchange to keys/nodes
    private void exch(int i, int j) {

	Key t = pq[i]; pq[i] = pq[j]; pq[j] = t;
    }


     // helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
    
}
