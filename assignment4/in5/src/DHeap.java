/**
 * @author Karl Rameld kara9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */
// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera.

public class DHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_MIN_HEAP_ON_EMPTY_PARAM = 2; // undvika "magic number"

    private int currentSize; // Number of elements in heap
    private T[] array; // The heap array
    private int nrOfChild;

    /**
     * Creates an empty DHeap with two children
     */
    public DHeap() {
        this(DEFAULT_MIN_HEAP_ON_EMPTY_PARAM);
    }

    /**
     * Creates an empty DHeap with a variable number of children
     * 
     * @param d : number of children
     */
    public DHeap(int d) {
        if (d < DEFAULT_MIN_HEAP_ON_EMPTY_PARAM)
            throw new IllegalArgumentException();
        currentSize = 0;
        this.nrOfChild = d;
        array = (T[]) new Comparable[DEFAULT_CAPACITY + 1];
    }

    /**
     * Finds the parameters parent index
     * 
     * @param index : the child index
     * @return the parents index
     */
    public int parentIndex(int index) {
        if (index < DEFAULT_MIN_HEAP_ON_EMPTY_PARAM)
            throw new IllegalArgumentException();

        while (index % nrOfChild > 3) // finds min index
            index--;

        return (index - 2 + nrOfChild) / nrOfChild;
    }

    /**
     * Finds the parameters first child index
     * 
     * @param index : the parent index
     * @return the first childs index
     */
    public int firstChildIndex(int index) {
        if (index < 1)
            throw new IllegalArgumentException();
        return nrOfChild * index - (nrOfChild - 2);
    }

    public int size() {
        return currentSize;
    }

    public T get(int index) {
        return array[index];
    }

    /**
     * Insert into the priority queue, maintaining heap order. Duplicates are
     * allowed.
     * 
     * @param x the item to insert.
     */
    public void insert(T x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        // Percolate up
        int hole = ++currentSize;
        for (array[0] = x; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
            array[hole] = array[parentIndex(hole)];
        array[hole] = x;
    }

    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Find the smallest item in the priority queue.
     * 
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public T findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     * 
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public T deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        T minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     * 
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }

    /**
     * Internal method to percolate down in the heap.
     * 
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        T tmp = array[hole];
        for (; firstChildIndex(hole) <= currentSize; hole = child) {

            child = firstChildIndex(hole);

            for (int i = child + 1; i <= currentSize && child != currentSize; i++) { // the plus 1 is not needed but results in the absolute minimual of miniscule preformance improvements. :)
                if (array[i].compareTo(array[child]) < 0) {                          
                    child = i;
                }

            }
            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }
}