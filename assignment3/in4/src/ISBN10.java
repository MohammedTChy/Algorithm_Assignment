
/**
 * @author Karl Rameld kara9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */
public class ISBN10 {

	static final int LENGTH = 10;
	static final int OVERLENGTH = 11;
	private char[] isbn;

	public ISBN10(String isbn) {
		if (isbn.length() != LENGTH)
			throw new IllegalArgumentException("Wrong length, must be 10");
		if (!checkDigit(isbn))
			throw new IllegalArgumentException("Not a valid isbn 10");
		this.isbn = isbn.toCharArray();
	}

	private boolean checkDigit(String isbn) {
		int sum = 0;
		for (int i = 0; i < LENGTH - 1; i++) {
			sum += Character.getNumericValue(isbn.charAt(i)) * (LENGTH - i);
		}
		int checkDigit = (OVERLENGTH - (sum % OVERLENGTH)) % OVERLENGTH;

		return isbn.endsWith(checkDigit == LENGTH ? "X" : "" + checkDigit);
	}

	@Override
	public boolean equals(Object object) { // TODO: finds fails faster now.
		if (object instanceof ISBN10) {
			ISBN10 o = (ISBN10) object;
			for (int i = 0; i < isbn.length; i++) {
				if (!(o.isbn[i] == this.isbn[i]))
					return false;
			}
			return true;
		}
		return false;

	}

	// This is okay since ISBN10 is a unique number
	@Override
	public int hashCode() {// TODO: re-do???
		//int sum = 0;
		//int prime = 31;
		//for (int i = 0; i < isbn.length; i++) {
		//	sum += isbn[i] * prime * (isbn.length - i);
		//}
		//return sum;
		return Integer.parseInt(this.toString()); // :)
	}

	@Override
	public String toString() {
		return new String(isbn);
	}
}
