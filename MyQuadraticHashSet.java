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
        int targetIndex = (table.length - 1);
        while(size > 0 && targetIndex >= 0){
            if(table[targetIndex] != REMOVED || table[targetIndex] != null){
                table[targetIndex] = REMOVED;
                numRemoved++;
                size--;
            }
            targetIndex--;
        }
    }

    /* SHOULD BE FINISHED! NOT TESTED YET! */
    public boolean contains(E e) {
        // TO DO
        int probeCount = 0;
        Object returnedValue;
        int index;

        while (probeCount < size) {
            index = probeIndex((Integer) e, probeCount, table.length);
            returnedValue = table[index];

            if(e.equals(returnedValue)){
                return true;
            } else if(returnedValue == null || returnedValue.equals(REMOVED)){
                return false;
            }

            probeCount++;
        }

        return false;
    }


    public boolean add(E e) {
        // TO DO
        if(contains(e)){
            return false;
        }

        // check to see if table needs to be resized and rehashed
        if(size + numRemoved >= thresholdSize){
            rehash();
        }

        int probeCount = 0;
        // Object returnedValue;
        int index;

        while(probeCount < size){
            index = probeIndex((Integer) e, probeCount, table.length);

            if(table[index] == null || table[index].equals(REMOVED)){
                table[index] = e;
                size++;
            }

            probeCount++;
        }

        return true;
    }


    // unchecked
    @SuppressWarnings("unchecked")
    private void rehash() {
        // TO DO
        nextTableSizeIndex++;
        Object[] tableCopy = new Object[tableSizes[nextTableSizeIndex]];
        size = 0;
        thresholdSize = (int)(table.length * maxLoadFactor);

        for(Object element : table){
            add((E)element);
        }
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

/* THE BELOW CODE WOULD WORK (for the contains(E e) method), BUT NOT AS INTENDED AND WOULD RUN REALLY SLOW.
        for (int i = 0; i < table.length; i++){
            if(e.equals(table[i])){
                return true;
            }
        }
        return false;
*/