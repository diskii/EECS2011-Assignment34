import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of an adaptable priority queue using an array-based heap.
 *  @author Jeffrey Young , CSE13175 , 211015146
 */
public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V>
		implements AdaptablePriorityQueue<K, V> {

	// ---------------- nested AdaptablePQEntry class ----------------
	/** Extension of the PQEntry to include location information. */
	protected static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
		private int index; // entry's current index within the heap

		/** Constructor **/
		public AdaptablePQEntry(K key, V value, int j) {
			super(key, value); // this sets the key and value
			index = j; // this sets the new field
		}

		/** Returns the index; O(1) time */
		public int getIndex() {
			return index;
		}

		/** Sets the index; O(1) time */
		public void setIndex(int j) {
			index = j;
		}
	} // ----------- end of nested AdaptablePQEntry class -----------

	/**
	 * Creates an empty adaptable priority queue using natural ordering of keys.
	 */
	public HeapAdaptablePriorityQueue() {
		super();
	}

	/**
	 * Creates an empty adaptable priority queue using the given comparator to
	 * order keys.
	 * 
	 * @param comp
	 *            comparator defining the order of keys in the priority queue
	 */
	public HeapAdaptablePriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	/**
	 * Validates an entry to ensure it is location-aware, O(1) time.
	 * 
	 * @param entry
	 *            an entry instance
	 * @return the entry cast as an AdaptablePQEntry instance
	 * @throws IllegalArgumentException
	 *             if the given entry was not valid
	 */
	protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry)
			throws IllegalArgumentException {
		if (!(entry instanceof AdaptablePQEntry))
			throw new IllegalArgumentException("Invalid entry");
		AdaptablePQEntry<K, V> locator = (AdaptablePQEntry<K, V>) entry; // safe
		int j = locator.getIndex();
		if (j >= heap.size() || heap.get(j) != locator)
			throw new IllegalArgumentException("Invalid entry");
		return locator;
	}

	/** Exchanges the entries at indices i and j of the array list, O(1) time */
	@Override
	protected void swap(int i, int j) {
		super.swap(i, j); // perform the swap
		((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(i); // reset entry's
															// index
		((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(j); // reset entry's
															// index
	}

	/**
	 * Restores the heap property by moving the entry at index j
	 * upward/downward, O(n) time
	 */
	protected void bubble(int j) {
		if (j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
			upheap(j);
		else
			downheap(j); // although it might not need to move
	}

	/**
	 * Inserts a key-value pair and return the entry created, O(n) time
	 * 
	 * @param key
	 *            the key of the new entry
	 * @param value
	 *            the associated value of the new entry
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException
	 *             if the key is unacceptable for this queue
	 */
	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key); // might throw an exception
		Entry<K, V> newest = new AdaptablePQEntry<>(key, value, heap.size());
		heap.add(newest); // add to the end of the list
		upheap(heap.size() - 1); // upheap newly added entry
		return newest;
	}

	/**
	 * Removes the given entry from the priority queue, O(1) time
	 * 
	 * @param entry
	 *            an entry of this priority queue
	 * @throws IllegalArgumentException
	 *             if e is not a valid entry for the priority queue.
	 */
	@Override
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		AdaptablePQEntry<K, V> locator = validate(entry);
		int j = locator.getIndex();
		if (j == heap.size() - 1) // entry is at last position
			heap.remove(heap.size() - 1); // so just remove it
		else {
			swap(j, heap.size() - 1); // swap entry to last position
			heap.remove(heap.size() - 1); // then remove it
			bubble(j); // and fix entry displaced by the swap
		}
	}

	/**
	 * Replaces the key of an entry, O(1) time
	 * 
	 * @param entry
	 *            an entry of this priority queue
	 * @param key
	 *            the new key
	 * @throws IllegalArgumentException
	 *             if e is not a valid entry for the priority queue.
	 */
	@Override
	public void replaceKey(Entry<K, V> entry, K key)
			throws IllegalArgumentException {
		AdaptablePQEntry<K, V> locator = validate(entry);
		checkKey(key); // might throw an exception
		locator.setKey(key); // method inherited from PQEntry
		bubble(locator.getIndex()); // with new key, may need to move entry
	}

	/**
	 * Replaces the value of an entry, O(1) time.
	 * 
	 * @param entry
	 *            an entry of this priority queue
	 * @param value
	 *            the new value
	 * @throws IllegalArgumentException
	 *             if e is not a valid entry for the priority queue.
	 */
	@Override
	public void replaceValue(Entry<K, V> entry, V value)
			throws IllegalArgumentException {
		AdaptablePQEntry<K, V> locator = validate(entry);
		locator.setValue(value); // method inherited from PQEntry
	}
}

/** HeapPriority Queue */
class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
	/** primary collection of priority queue entries */
	protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

	/**
	 * Creates an empty priority queue based on the natural ordering of its
	 * keys.
	 */
	public HeapPriorityQueue() {
		super();
	}

	/**
	 * Creates an empty priority queue using the given comparator to order keys.
	 * 
	 * @param comp
	 *            comparator defining the order of keys in the priority queue
	 */
	public HeapPriorityQueue(Comparator<K> comp) {
		super(comp);
	}

	/**
	 * Creates a priority queue initialized with the respective key-value pairs.
	 * The two arrays given will be paired element-by-element. They are presumed
	 * to have the same length. (If not, entries will be created only up to the
	 * length of the shorter of the arrays), O(n) time
	 * 
	 * @param keys
	 *            an array of the initial keys for the priority queue
	 * @param values
	 *            an array of the initial values for the priority queue
	 */
	public HeapPriorityQueue(K[] keys, V[] values) {
		super();
		for (int j = 0; j < Math.min(keys.length, values.length); j++)
			heap.add(new PQEntry<>(keys[j], values[j]));
		heapify();
	}

	/** Returns the parent, O(1) time */
	protected int parent(int j) {
		return (j - 1) / 2;
	}

	/** Returns the left, O(1) time */
	protected int left(int j) {
		return 2 * j + 1;
	}

	/** Returns the right, O(1) time */
	protected int right(int j) {
		return 2 * j + 2;
	}

	/** Returns the hasLeft, O(1) time */
	protected boolean hasLeft(int j) {
		return left(j) < heap.size();
	}

	/** Returns the hasRight, O(1) time */
	protected boolean hasRight(int j) {
		return right(j) < heap.size();
	}

	/** Exchanges the entries at indices i and j of the array list, O(1) time */
	protected void swap(int i, int j) {
		Entry<K, V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/**
	 * Moves the entry at index j higher, if necessary, to restore the heap
	 * property, O(n) time.
	 */
	protected void upheap(int j) {
		while (j > 0) { // continue until reaching root (or break statement)
			int p = parent(j);
			if (compare(heap.get(j), heap.get(p)) >= 0)
				break; // heap property verified
			swap(j, p);
			j = p; // continue from the parent's location
		}
	}

	/**
	 * Moves the entry at index j lower, if necessary, to restore the heap
	 * property, O(n) time.
	 */
	protected void downheap(int j) {
		while (hasLeft(j)) { // continue to bottom (or break statement)
			int leftIndex = left(j);
			int smallChildIndex = leftIndex; // although right may be smaller
			if (hasRight(j)) {
				int rightIndex = right(j);
				if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
					smallChildIndex = rightIndex; // right child is smaller
			}
			if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
				break; // heap property has been restored
			swap(j, smallChildIndex);
			j = smallChildIndex; // continue at position of the child
		}
	}

	/** Performs a bottom-up construction of the heap in linear time, O(n) time */
	protected void heapify() {
		int startIndex = parent(size() - 1); // start at PARENT of last entry
		for (int j = startIndex; j >= 0; j--)
			downheap(j);
	}

	/**
	 * Returns the number of items in the priority queue, O(1) time.
	 * 
	 * @return number of items
	 */
	@Override
	public int size() {
		return heap.size();
	}

	/**
	 * Returns (but does not remove) an entry with minimal key, O(1) time.
	 * 
	 * @return entry having a minimal key (or null if empty)
	 */
	@Override
	public Entry<K, V> min() {
		if (heap.isEmpty())
			return null;
		return heap.get(0);
	}

	/**
	 * Inserts a key-value pair and return the entry created, O(n) time.
	 * 
	 * @param key
	 *            the key of the new entry
	 * @param value
	 *            the associated value of the new entry
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException
	 *             if the key is unacceptable for this queue
	 */
	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key); // auxiliary key-checking method (could throw exception)
		Entry<K, V> newest = new PQEntry<>(key, value);
		heap.add(newest); // add to the end of the list
		upheap(heap.size() - 1); // upheap newly added entry
		return newest;
	}

	/**
	 * Removes and returns an entry with minimal key, O(n) time.
	 * 
	 * @return the removed entry (or null if empty)
	 */
	@Override
	public Entry<K, V> removeMin() {
		if (heap.isEmpty())
			return null;
		Entry<K, V> answer = heap.get(0);
		swap(0, heap.size() - 1); // put minimum item at the end
		heap.remove(heap.size() - 1); // and remove it from the list;
		downheap(0); // then fix new root
		return answer;
	}

	/** Used for debugging purposes only, O(n) time */
	@SuppressWarnings("unused")
	private void sanityCheck() {
		for (int j = 0; j < heap.size(); j++) {
			int left = left(j);
			int right = right(j);
			if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0)
				System.out.println("Invalid left child relationship");
			if (right < heap.size()
					&& compare(heap.get(right), heap.get(j)) < 0)
				System.out.println("Invalid right child relationship");
		}
	}
}
