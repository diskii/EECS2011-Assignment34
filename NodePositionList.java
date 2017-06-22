
import java.util.Iterator;

/**
 * PositionList using a doubly-linked list of nodes.
 *  @author Jeffrey Young , CSE13175 , 211015146
 */

public class NodePositionList<E> implements PositionList<E> {

	protected int size;
	protected DNode<E> header, trailer;

	/** Constructor that creates an empty list; O(1) time */
	public NodePositionList() {
		size = 0;
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(header, null, null);
		header.setNext(trailer);
	}

	/**
	 * Checks if position is valid for this list and converts it to DNode if it
	 * is valid; O(1) time
	 */
	protected DNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null)
			throw new InvalidPositionException(
					"Null position passed to NodeList");
		if (p == header)
			throw new InvalidPositionException(
					"The header node is not a valid position");
		if (p == trailer)
			throw new InvalidPositionException(
					"The trailer node is not a valid position");
		try {
			DNode<E> temp = (DNode<E>) p;
			if ((temp.getPrev() == null) || (temp.getNext() == null))
				throw new InvalidPositionException(
						"Position does not belong to a valid NodeList");
			return temp;
		} catch (ClassCastException e) {
			throw new InvalidPositionException(
					"Position is of wrong type for this list");
		}
	}

	/** Returns the number of elements in the list; O(1) time */
	public int size() {
		return size;
	}

	/** Returns whether the list is empty; O(1) time */
	public boolean isEmpty() {
		return (size == 0);
	}

	/** Returns the first position in the list; O(1) time */
	public Position<E> first() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException("List is empty");
		return header.getNext();
	}

	/** Returns the last position in the list; O(1) time */
	public Position<E> last() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException("List is empty");
		return trailer.getPrev();
	}

	/** Returns the position before the given one; O(1) time */
	public Position<E> prev(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		DNode<E> v = checkPosition(p);
		DNode<E> prev = v.getPrev();
		if (prev == header)
			throw new BoundaryViolationException(
					"Cannot advance past the beginning of the list");
		return prev;
	}

	/** Returns the position after the given one; O(1) time */
	public Position<E> next(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		DNode<E> v = checkPosition(p);
		DNode<E> next = v.getNext();
		if (next == trailer)
			throw new BoundaryViolationException(
					"Cannot advance past the end of the list");
		return next;
	}

	/**
	 * Insert the given element before the given position; O(1) time
	 */
	public void addBefore(Position<E> p, E element)
			throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		size++;
		DNode<E> newNode = new DNode<E>(v.getPrev(), v, element);
		v.getPrev().setNext(newNode);
		v.setPrev(newNode);
	}

	/**
	 * Insert the given element after the given position; O(1) time
	 */
	public void addAfter(Position<E> p, E element)
			throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		size++;
		DNode<E> newNode = new DNode<E>(v, v.getNext(), element);
		v.getNext().setPrev(newNode);
		v.setNext(newNode);
	}

	/**
	 * Insert the given element at the beginning of the list, returning the new
	 * position; O(1) time
	 */
	public void addFirst(E element) {
		size++;
		DNode<E> newNode = new DNode<E>(header, header.getNext(), element);
		header.getNext().setPrev(newNode);
		header.setNext(newNode);
	}

	/**
	 * Insert the given element at the end of the list, returning the new
	 * position; O(1) time
	 */
	public void addLast(E element) {
		size++;
		DNode<E> oldLast = trailer.getPrev();
		DNode<E> newNode = new DNode<E>(oldLast, trailer, element);
		oldLast.setNext(newNode);
		trailer.setPrev(newNode);
	}

	/** Remove the given position from the list; O(1) time */
	public E remove(Position<E> p) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		size--;
		DNode<E> vPrev = v.getPrev();
		DNode<E> vNext = v.getNext();
		vPrev.setNext(vNext);
		vNext.setPrev(vPrev);
		E vElem = v.element();
		v.setNext(null);
		v.setPrev(null);
		return vElem;
	}

	/**
	 * Replace the element at the given position with the new element and return
	 * the old element; O(1) time
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		E oldElt = v.element();
		v.setElement(element);
		return oldElt;
	}

	/** Returns an iterator of all the elements in the list. */
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	/** Returns an iterable collection of all the nodes in the list. */
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> P = new NodePositionList<Position<E>>();
		if (!isEmpty()) {
			Position<E> p = first();
			while (true) {
				P.addLast(p);
				if (p == last())
					break;
				p = next(p);
			}
		}
		return P;
	}

	/** Returns whether a position is the first one; O(1) time */
	public boolean isFirst(Position<E> p) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		return v.getPrev() == header;
	}

	/** Returns whether a position is the last one; O(1) time */
	public boolean isLast(Position<E> p) throws InvalidPositionException {
		DNode<E> v = checkPosition(p);
		return v.getNext() == trailer;
	}

	/** Swap the elements of two give positions; O(1) time */
	public void swapElements(Position<E> a, Position<E> b)
			throws InvalidPositionException {
		DNode<E> pA = checkPosition(a);
		DNode<E> pB = checkPosition(b);
		E temp = pA.element();
		pA.setElement(pB.element());
		pB.setElement(temp);
	}

	/** Returns a textual representation of a given node list using for-each */
	public static <E> String forEachToString(PositionList<E> L) {
		String s = "[";
		int i = L.size();
		for (E elem : L) {
			s += elem;
			i--;
			if (i > 0)
				s += ", ";
		}
		s += "]";
		return s;
	}

	/** Returns a textual representation of a given node list */
	public static <E> String toString(PositionList<E> l) {
		Iterator<E> it = l.iterator();
		String s = "[";
		while (it.hasNext()) {
			s += it.next();
			if (it.hasNext())
				s += ", ";
		}
		s += "]";
		return s;
	}

	/** Returns a textual representation of the list */
	public String toString() {
		return toString(this);
	}
}
