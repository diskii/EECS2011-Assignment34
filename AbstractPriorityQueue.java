import java.util.Comparator;

/**
 * An abstract base class to ease the implementation of the PriorityQueue
 * interface.
 * 
 * The base class provides four means of support: 1) It defines a PQEntry class
 * as a concrete implementation of the entry interface 2) It provides an
 * instance variable for a general Comparator and a protected method, compare(a,
 * b), that makes use of the comparator. 3) It provides a boolean checkKey
 * method that verifies that a given key is appropriate for use with the
 * comparator 4) It provides an isEmpty implementation based upon the abstract
 * size() method.
 *  @author Jeffrey Young , CSE13175 , 211015146
 */
public abstract class AbstractPriorityQueue<K, V> implements
		PriorityQueue<K, V> {

	/**
	 * A concrete implementation of the Entry interface to be used within a
	 * PriorityQueue implementation.
	 */
	public static class PQEntry<K, V> implements Entry<K, V> {
		private K k; // key
		private V v; // value

		/** Constructor **/
		public PQEntry(K key, V value) {
			k = key;
			v = value;
		}

		/** Returns the key; O(1) time */
		public K getKey() {
			return k;
		}

		/** Returns the value; O(1) time */
		public V getValue() {
			return v;
		}

		/** Sets the key; O(1) time */
		protected void setKey(K key) {
			k = key;
		}

		/** Sets the value; O(1) time */
		protected void setValue(V value) {
			v = value;
		}
	} // ----------- end of nested PQEntry class -----------

	/** The comparator defining the ordering of keys in the priority queue. */
	private Comparator<K> comp;

	/**
	 * Creates an empty priority queue using the given comparator to order keys;
	 * O(1) time.
	 * 
	 * @param comparator
	 *            defining the order of keys in the priority queue
	 */
	protected AbstractPriorityQueue(Comparator<K> c) {
		comp = c;
	}

	/**
	 * Creates an empty priority queue based on the natural ordering of its
	 * keys.
	 */
	protected AbstractPriorityQueue() {
		this(new DefaultComparator<K>());
	}

	/** Method for comparing two entries according to key; O(1) time */
	protected int compare(Entry<K, V> a, Entry<K, V> b) {
		return comp.compare(a.getKey(), b.getKey());
	}

	/** Determines whether a key is valid; O(1) time. */
	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key, key) == 0); // see if key can be compared
													// to itself
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	/**
	 * Tests whether the priority queue is empty; O(1) time.
	 * 
	 * @return true if the priority queue is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
}
