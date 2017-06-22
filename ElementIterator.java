
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

/**
 * A simple iterator class for lists. The elements of a list are returned by
 * this iterator. No copy of the list is made, so any changes to the list are
 * reflected in the iterator. 
 *  @author Jeffrey Young , CSE13175 , 211015146
 */

public class ElementIterator<E> implements Iterator<E> {
	protected PositionList<E> list; // the underlying list
	protected Position<E> cursor; // the next position

	/** Creates a constructor of an element iterator over the given list. */
	public ElementIterator(PositionList<E> L) {
		list = L;
		cursor = (list.isEmpty()) ? null : list.first();
	}

	/** Returns whether the iterator has a next object, O(1) time */
	public boolean hasNext() {
		return (cursor != null);
	}

	/** Returns the next object in the iterator, O(1) time */
	public E next() throws NoSuchElementException {
		if (cursor == null)
			throw new NoSuchElementException("No next element");
		E toReturn = cursor.element();
		cursor = (cursor == list.last()) ? null : list.next(cursor);
		return toReturn;
	}

	/**
	 * Throws an {@link UnsupportedOperationException} in all cases, because
	 * removal is not a supported operation in this iterator, O(1) time
	 */
	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("remove");
	}
}
