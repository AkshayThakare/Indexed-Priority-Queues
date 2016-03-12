import java.lang.Comparable;
/**
 * The PriorityQueueIndexed class implements methods like insert, add ,remove ,deleteMin ,decreaseKey , min, percolateUp, percolateDown, buildHeap
 * @author  Akshay Thakare
 * @version 1.0, March 2015 
 */
public class PriorityQueueIndexed<T extends Comparable<? super T> & PQIndex> {
	T[] queue;
	int size=0;

	/** Build a priority queue with a given array q */
	PriorityQueueIndexed(T[] q) {
		this(q.length);// call to default constructor, to initialize an array of size q.length
		for(int i=1; i<q.length; i++){
			queue[i] = q[i];
		}
		size=q.length-1;
	}

	/** Create an empty priority queue of given maximum size */
	@SuppressWarnings("unchecked")
	PriorityQueueIndexed(int n) {
		queue= (T[]) new Comparable[n];
		//size=n-1;
	}
	/**
	 * inserts x in the priority queue by calling insert()
	 * @param   x
	 * @return
	 */
	void insert(T x) {
		add(x);
	}
	/**
	 * inserts x in the priority queue by calling percolateUp() and increases size by 1
	 * @param   x
	 * @return
	 */
	void add(T x) {
		queue[0] = x; //copy this new element at 0th position
		int holeIndex = size+1; //position where the new element should be inserted
		/*call Percolate up to fix the position of new element*/
		queue[holeIndex] = x;
		percolateUp(holeIndex);
		size++;
	}
	/**
	 * removes and returns min element from priority queue by calling deleteMin()
	 * @param   
	 * @return T
	 */
	T remove() {
		return deleteMin();
	}
	/**
	 * removes and returns min element from the priority queue
	 * @param   
	 * @return T
	 */
	T deleteMin() {
		/*Remove the first element from the queue*/
		T minElement=min();
		if(size>0){
			minElement = min();
			queue[1] = queue[size]; //insert the last element at the 1st position and call percolateDown(1)
			queue[1].putIndex(1);
			// reducing the size of queue by 1
			size--;
			// calling the percolate down method to fix the correct position
			percolateDown(1);
		}
		return minElement;
	}
	/**
	 * restore heap order property after the priority of x has decreased after doing insert(x) operation
	 * @param  T x
	 * @return 
	 */
	void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}
	/**
	 * returns the min element(ie. queue[1])
	 * @param 
	 * @return T
	 */
	T min() { 
		return queue[1];
		//return null;
	}

	/**
	 * restore heap order property after the priority of x has been decreased after doing insert(x) operation
	 * @param  T x
	 * @return 
	 */
	void percolateUp(int i) {
		T x = queue[i]; //the element to be percolated up
		//run the loop till we find the correct position for the new element
		while(i>1 && queue[i/2]!=null && queue[i/2].compareTo(x) > 0){
			//percolate up the hole as element at parent Index is greater than x
			queue[i] = queue[i/2];
			//change the index of x to its parent's index(i/2)
			queue[i].putIndex(i);
			i = i/2;//repeat this to get elements greater than x
		}

		//put the new element x to the hole(correct position now)
		queue[i] = x;
		queue[i].putIndex(i);
	}

	/** Create heap order for sub-heap rooted at node i.  Precondition: Heap order may be violated 
	 *  at node i, but its children are roots of heaps that are fine.  Restore heap property */
	/**
	 * restore heap order property after deleteMin() operation
	 * @param index i
	 * @return 
	 */
	void percolateDown(int i) {
		T x = queue[i]; //element to be percolated down
		int childIndex = 2*i; // index of the first child
		//int parentIndex = x.getIndex();//parent index
		T minChild;
		int minChildIndex=0;

		if(childIndex > size){
			//no children, hence nothing to do
			return;
		}else if(childIndex == size){
			// if only one child, then compare that with x and swap if x is smaller than the child
			if(x.compareTo(queue[childIndex]) > 0){
				T temp = queue[childIndex];
				queue[childIndex] = x; //Percolate down the larger element
				queue[childIndex].putIndex(temp.getIndex());//change the index of x to child's index
				queue[i] = temp; 
				queue[i].putIndex(i); //change the index of child to x's index
			}
		}else{ /* at least two child are present in the queue*/

			if(queue[childIndex].compareTo(queue[childIndex+1]) < 0){
				/*Left child is smaller than right*/
				minChild = queue[childIndex];
				minChildIndex = childIndex;
			}else{
				/*Right child is smaller that left*/
				minChild = queue[childIndex+1];
				minChildIndex = childIndex+1;
			}

			/*Compare x with the minimum child*/
			if(x.compareTo(minChild) > 0){
				T temp = queue[minChildIndex]; //storing the minChild
				queue[minChildIndex] = x; // copy x to minChildIndex
				queue[minChildIndex].putIndex(minChildIndex);//change index of x to index of minChildIndex
				queue[i] = temp;//copy minChild to correct location
				queue[i].putIndex(i);//change the child's index to x's index
				percolateDown(minChildIndex);
			}
		}
	}
	
	/** Create a heap.  Precondition: none.  Array may violate heap order in many places. */
	/**
	 * builds heap by calling percolateDown() on all non leaf elements till the root
	 * @param arr[]
	 * @return 
	 */
	void buildHeap(T[] arr) {
		this.size=arr.length-1;
		for(int i=1; i<size; i++){
			this.queue[i] = arr[i];
		}
		for(int i=(size-1)/2; i>=1; i--){
			percolateDown(i);
		}
	}
}
