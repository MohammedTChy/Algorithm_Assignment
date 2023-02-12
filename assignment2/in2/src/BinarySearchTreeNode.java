/**
 * @author Karl Rameld kara9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */

/**
 *
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att paketdeklarationen måste plockas bort vid inlämningen för
 * att koden ska gå igenom de automatiska testerna.
 *
 * De ändringar som är tillåtna är begränsade av följande:
 * <ul>
 * <li>Ni får INTE byta namn på klassen.
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans. Detta gäller också alterntiv
 * till loopar, så som strömmar.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * <li>Ni får INTE låta NÅGON metod ta en parameter av typen
 * BinarySearchTreeNode. Enbart den generiska typen (T eller vad ni väljer att
 * kalla den), String, StringBuilder, StringBuffer, samt primitiva typer är
 * tillåtna.
 * </ul>
 *
 * @author henrikbe
 *
 * @param <T>
 */

 /**
  * This is very heavily based on the books implementation. 
  */

public class BinarySearchTreeNode<T extends Comparable<T>> { 

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		if (data == null)
			return false;

		int compareResult = data.compareTo(this.data);

		if (compareResult < 0) {
			if (left == null) {
				left = new BinarySearchTreeNode<>(data);
				return true;
			} else
				return left.add(data);
		}

		if (compareResult > 0) {
			if (right == null) {
				right = new BinarySearchTreeNode<>(data);
				return true;
			} else
				return right.add(data);
		}

		return false;
	}

	private T findMin() {
		if (data == null)
			return null;

		if (left == null)
			return data;
		else
			return left.findMin();

	}

	public BinarySearchTreeNode<T> remove(T data) {
		if (this.data == null)
			return this;

		int compareResult = data.compareTo(this.data);

		if (compareResult < 0) {
			if (left == null)
				return this;
			else
				left = left.remove(data);
		} else if (compareResult > 0) {
			if (right == null)
				return this;
			else
				right = right.remove(data);
		} else if (this.left != null && this.right != null) {
			this.data = right.findMin();
			right = right.remove(this.data);
		} else {
			if (left == null)
				return right;
			else
				return left;
		}
		return this;
	}

	public boolean contains(T data) {
		if (data == null)
			return false;

		int compareResult = data.compareTo(this.data);
		if (compareResult < 0) {
			if (left == null) {
				return false;
			} else
				return left.contains(data);
		}

		if (compareResult > 0) {
			if (right == null) {
				return false;
			} else
				return right.contains(data);
		}

		return true;
	}

	public int size() {
		if (left == null && right == null)
			return 1;

		if (left == null)
			return right.size() + 1;

		if (right == null)
			return left.size() + 1;
		else
			return left.size() + 1 + right.size();
	}

	public int depth() {
		if (left == null && right == null)
			return 0;

		if (left == null)
			return right.depth() + 1;

		if (right == null)
			return left.depth() + 1;
		else
			return 1 + Math.max(left.depth(), right.depth());
	}

	public String toString() {
		String div = ", ";
		if (left == null && right == null)
			return data.toString();

		if (left == null)
			return data.toString() + div + right.toString();

		if (right == null)
			return left.toString() + div + data.toString();
		else
			return left.toString() + div + data.toString() + div + right.toString();
	}
}
