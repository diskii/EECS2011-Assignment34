/**
 * 
 * @author Jeffrey Young , CSE13175 , 211015146
 *
 */
public class DNode<E> implements Position<E> {
	private DNode<E> prev, next; // References to the nodes before and after
	private E element; // Element stored in this position

	/**
	 * Constructor that creates a node with given fields
	 * 
	 * @param newPrev
	 *            - previous
	 * @param newNext
	 *            - next
	 * @param elem
	 *            - element
	 */
	public DNode(DNode<E> newPrev, DNode<E> newNext, E elem) {
		prev = newPrev;
		next = newNext;
		element = elem;
	}

	/**
	 * Method from interface Position
	 */
	public E element() throws InvalidPositionException {
		if ((prev == null) && (next == null))
			throw new InvalidPositionException("Position is not in a list!");
		return element;
	}

	/**
	 * Accessor Method of getting the next element; O(1) time
	 * 
	 * @return next element
	 */
	public DNode<E> getNext() {
		return next;
	}

	/**
	 * Accessor Method of getting the previous element; O(1) time
	 * 
	 * @return the previous element
	 */
	public DNode<E> getPrev() {
		return prev;
	}

	/**
	 * SetNext method updates the next element by setting it.; O(1) time
	 * 
	 * @param newNext
	 *            sets the next element
	 */
	public void setNext(DNode<E> newNext) {
		next = newNext;
	}

	/**
	 * SetPrev method updates the previous element by setting it; O(1) time
	 * 
	 * @param newPrev
	 *            sets the previous element
	 */
	public void setPrev(DNode<E> newPrev) {
		prev = newPrev;
	}

	/**
	 * SetElement method sets the element; O(1) time
	 * 
	 * @param newElement
	 *            setting the element
	 */
	public void setElement(E newElement) {
		element = newElement;
	}
}
