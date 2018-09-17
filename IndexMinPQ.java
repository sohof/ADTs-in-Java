import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>  {

    // binary heap using 1-based indexing. I.e we start from pq[1]. pq[i] = gives the index/says where on
    // the keys array, item with positon "i" on prio.queue is located. E.g pq[1] = 12. Says pq elem 1, which is the min.elem of
    // of the pq is located at index 12 in the keys array. 
    private int[] pq;

    // inverse: qp[pq[i]] = pq[qp[i]] = i. qp[i] says where/at which index item i, is located on the prio.queue
    // So given an element in the keys arrays, say elem i, we can find where on the pq it is placed.                   
    private int[] qp;
    
    private Key[] keys;      // Items with priorities
    private int N;           // number of items on priority queue
    private int maxN;        // max nr of item on queue
    

    // Initializes an empty indexed priority queue with indices between 0 
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
	N = 0;
	keys = (Key[]) new Comparable[maxN + 1];
	pq = new int[maxN + 1];
	qp = new int[maxN + 1];

	for (int i = 0; i <= maxN; i++) {
	    qp[i]= -1;  // convention: qp[i] = -1, if i is not on the prio.queue. 
	}
    }

    public boolean isEmpty() {return N == 0; }
    
    public int size() {return N; }

    public int getQP(int i) {return qp[i]; }
    
    public int getPQ(int i) {return pq[i]; }    
    
    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        N++;
        qp[i] = N;
        pq[N] = i;
        keys[i] = key;
        swim(N);
    }

    // Removes a minimum key and returns its associated index.
    public int delMin() {
	if (N == 0) throw new NoSuchElementException("Priority queue underflow");

	int min = pq[1];
	exch(1, N--);          //exchange with last item, then decrement nr of items
	sink(1);
	assert min == pq[N+1];
	qp[min]= -1;          // mark as deleted in qp 
	keys[min] = null;     // Avoid loitering. Help with garbage collection.
	pq[N+1] = -1;        // not really needed. Extra precation,put a sentinel value there.
	return min; 
    }

    // Returns the minimum key, without deleting.
    public Key minKey() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    // Returns what index the min item/key has in the keys array. 
    public int minIndex() {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // Remove the key associated with index i
    public void delete(int i) {
	if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	
	int indexOnPQ = qp[i]; // where on pq is item i ? 
        
	exch(indexOnPQ, N--); // change with last item, then decrement N, i.e nr of items. 
	swim(indexOnPQ);
	sink(indexOnPQ);
	keys[i]= null; 
	qp[i] = -1;  // item i is no longer on prio.queue. 
    }

    // Returns the key associated with index i.
    public Key keyOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i];
    }
    // Increase the key associated with index i to the specified value.
    public void increaseKey(int i, Key key) {
	if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	if (keys[i].compareTo(key) >= 0 ) // check if arg key > than the one it is meant to increase
	  throw new IllegalArgumentException("Calling increaseKey() with arg. that wouldn't strictly increase the key");

	keys[i] = key;
        sink(qp[i]);  // sink elem i on the prio.queue. Becasue this is a min.PQ!	    
	
    }
    // Decrease the key associated with index i to the specified value.
    public void decreaseKey(int i, Key key) {
	if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	if (keys[i].compareTo(key) <= 0 ) // check if arg key < than the one it is meant to decrease
	  throw new IllegalArgumentException("Calling decreaseKey() with arg. that wouldn't strictly decrease the key");

	keys[i] = key;
        swim(qp[i]);  // sink elem i on the prio.queue. Becasue this is a min.PQ!	    
	
    }

    //  Is i an index on this priority queue?
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();

	return (qp[i] != -1); 
    }

    /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    // Bottom-up reheapify 
    private void swim(int k) {

	// k/2 is the parent of k
	while(k > 1 && greater(k/2, k)) {   

	    exch(k, k/2); // parent was greater than k, so exchange
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

   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean greater(int i, int j) {

	return keys[pq[i]].compareTo(keys[pq[j]]) > 0 ;
    }

    // Helper function to exchange to keys/nodes
    private void exch(int i, int j) {

	int swap = pq[i];
	pq[i] = pq[j];
	pq[j] = swap;
	qp[pq[i]] = i;
	qp[pq[j]] = j;

    }


    /***************************************************************************
    * Iterators.
    ***************************************************************************/

    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

 
      
}//end of class

    
