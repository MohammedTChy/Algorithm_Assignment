/**
 * @author Karl Rameld kara9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */

/*
 * Denna klass ska förberedas för att kunna användas som nyckel i en hashtabell.
 * Du får göra nödvändiga ändringar även i klasserna MyString och ISBN10.
 * 
 * Hashkoden ska räknas ut på ett effektivt sätt och följa de regler och
 * rekommendationer som finns för hur en hashkod ska konstrueras. Notera i en
 * kommentar i koden hur du har tänkt när du konstruerat din hashkod.
 */
public class Book {
	private MyString title;
	private MyString author;
	private ISBN10 isbn;
	private MyString content;
	private int price;

	public Book(String title, String author, String isbn, String content, int price) {
		this.title = new MyString(title);
		this.author = new MyString(author);
		this.isbn = new ISBN10(isbn);
		this.content = new MyString(content);
		this.price = price;
	}

	public MyString getTitle() {
		return title;
	}

	public MyString getAuthor() {
		return author;
	}

	public ISBN10 getIsbn() {
		return isbn;
	}

	public MyString getContent() {
		return content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * This is based on the default String hashCode
	 * https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#hashCode--
	 * The reason to not simply multiply every char with 31 is that "ABC" and "CBA"
	 * would get the same hash value. Another example would be to multiply every
	 * char with an ascending or descending prime number.
	 */

	@Override
	public boolean equals(Object object) {// TODO: Redo
		if (!(object instanceof Book))
			return false;
		Book other = (Book) object;
		return isbn.equals(other.isbn); // Just call ISBN and get the result from it if everything above checks out (if
										// statement)
	}

	/*
	 * It just take whatever the result is from ISBN
	 * It is okay to use ISBN10 only here becasue every book except reprints get an unique ISBN number,
	 * and reprints are contentwise identical. 
	 */
	@Override
	public int hashCode() { 
		return isbn.hashCode();
	}

	/*
	 * @Override public int hashCode() { int sum = 0; char[] temp =
	 * isbn.toString().toCharArray(); for(int i = 0; i<temp.length; i++){ sum +=
	 * Math.pow(Character.getNumericValue(temp[i]), 1+temp.length - i)*31; } return
	 * sum; }
	 * 
	 * @Override public boolean equals(Object obj) { return this.hashCode() ==
	 * obj.hashCode(); }
	 */
	@Override
	public String toString() {
		return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn,
				content.length());
	}

}
