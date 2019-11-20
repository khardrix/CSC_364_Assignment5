package hash;

// MyQuadraticHashSet.java
// Implements a hash table of objects, using open addressing with quadratic probing.
// - Jeff Ward

public class MyQuadraticHashSet<E> implements MySet<E> {
    private int size = 0; // The number of elements in the set

    private Object[] table; // The table elements. Initially all null.

    private double maxLoadFactor; // size should not exceed (table.length *
    // maxLoadFactor)

    private int thresholdSize; // (int) (table.length * maxLoadFactor)
    // When size + numRemoved exceeds this value,
    // then resize and rehash.

    private int[] tableSizes; // A set of prime numbers that will be used as the
    // hash table sizes.

    private int nextTableSizeIndex = 0; // Index into tableSizes.

    /*
     * REMOVED is stored in the hashTable in place of a removed element. This
     * lets the quadratic probing process know that it may need to continue
     * probing.
     */

    /** NOTE ADDED BY ME:
     * If you delete a key, then search ( contains(E e) ) may fail.
     * So, if you delete, its bucket should be marked "REMOVED" and not empty.
     * You can insert ( add(E e) ) into a bucket marked as "REMOVED", but
     * the search ( contains(E e) ) operation should not stop on this bucket.
     */
    private final static Object REMOVED = new Object();

    private int numRemoved = 0; // The number of times that REMOVED occurs in the table.

    private int probeIndex(int hashCode, long probeCount, int tableLength) {
        return (int) ((hashCode % tableLength + tableLength + probeCount * probeCount) % tableLength);
    }


    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public MyQuadraticHashSet(double maxLoadFactor, int[] tableSizes) {
        // TO DO
        this.maxLoadFactor = maxLoadFactor;
        this.tableSizes = tableSizes;
        this.table = new Object[17]; /* Could also be: this.table = new Object[this.tableSizes[0]]; */
        this.thresholdSize = (int) (table.length * maxLoadFactor); /* I think this is right! */
    }


    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public void clear() {
        // TO DO
        while(size > 0){
            table[(this.size - 1)] = REMOVED;
            numRemoved++;
            size--;
        }
    }

    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public boolean contains(E e) {
        // TO DO
        for (int i = 0; i < table.length; i++){
            if(e.equals(table[i])){
                return true;
            }
        }
        return false;
    }


    public boolean add(E e) {
        // TO DO
        return false; /*//////////////////////// TEMPORARY VALUE TO CLEAR ERRORS!!!!!!! /////////////////////////////*/
    }


    @SuppressWarnings("unchecked")
    private void rehash() {
        // TO DO
    }


    public boolean remove(E e) {
        // TO DO
        return false; /*//////////////////////// TEMPORARY VALUE TO CLEAR ERRORS!!!!!!! /////////////////////////////*/
    }


    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public boolean isEmpty() {
        // TO DO
        return size == 0;
    }

    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public int size() {
        // TO DO
        return this.size;
    }


    public java.util.Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }
}