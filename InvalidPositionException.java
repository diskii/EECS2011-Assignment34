/**
 *  @author Jeffrey Young , CSE13175 , 211015146
 */
@SuppressWarnings("serial")
public class InvalidPositionException extends RuntimeException {
	public InvalidPositionException(String err) {
		super(err);
	}

	public InvalidPositionException() {
	}
}
