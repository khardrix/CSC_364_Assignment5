package hash;

/**********************************************************************************************************************
 **********************************************************************************************************************
 *****     Course: CSC-364-002      Semester: Fall 2019      Professor: Rasib Khan      Student: Ryan Huffman     *****
 *****------------------------------------------------------------------------------------------------------------*****
 *****                           Programming Assignment #5: Quadratic Hashing Program                             *****
 *****____________________________________________________________________________________________________________*****
 *****                 The goal of this assignment is to write this class to use quadratic probing                *****
 *****                           to check for elements, add elements and delete elements                          *****
 **********************************************************************************************************************
 *********************************************************************************************************************/

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

    private final static Object REMOVED = new Object();

    private int numRemoved = 0; // The number of times that REMOVED occurs in the table.

    private int probeIndex(int hashCode, long probeCount, int tableLength) {
        return (int) ((hashCode % tableLength + tableLength + probeCount * probeCount) % tableLength);
    }

    // Constructor
    public MyQuadraticHashSet(double maxLoadFactor, int[] tableSizes) {
        // TO DO
        this.maxLoadFactor = maxLoadFactor;
        this.tableSizes = tableSizes;
        this.table = new Object[17];    // same as: this.table = new Object[tableSizes[nextTableSizeIndex]];
        this.thresholdSize = (int) Math.ceil(table.length * maxLoadFactor); // set the threshold and round up
    }

    public void clear() {
        // TO DO
        size = 0;                                               // reset size to 0
        table = new Object[tableSizes[nextTableSizeIndex]];     // instantiate a new table Object
    }

    // check if "table" contains the parameter element "e"
    public boolean contains(E e) {
        // TO DO
        // if the table is empty, it cannot contain "e" or any element for that matter
        if(isEmpty()){
            return false;
        } else{
            // declare and initialize probeCount to 0
            int probeCount = 0;

            // while loop to go through table looking for element "e"
            while(probeCount <= size){
                // calculate the index of table to check
                int index = probeIndex(e.hashCode(), probeCount, table.length);

                // if "e" is equal to table[index], then the table does contain "e"
                if(e.equals(table[index])){
                    return true;
                }
                // should not be probing into an index that equals null if "e" is in table
                else if(table[index] == null){
                    return false;
                }
                // if table[index] equals anything other than "e" or null, increment probeCount and probe again
                else{
                    probeCount++;
                }
            }
            // if this instruction is reached, table did not contain "e"
            return false;
        }
    }

    // method to add elements to the Object array hash table, "table"
    public boolean add(E e) {
        // TO DO
        // check if table already contains "e" and if so, do not add it and return false
        if(contains(e)){
            return false;
        }

        // if the number of elements plus the number of instances of REMOVED plus the element about to insert
            // exceeds thresholdSize, resize and rehash table
        if((size + numRemoved) >= thresholdSize){
            rehash();
        }

        // declare and initialize probeCount to 0
        int probeCount = 0;
        // increment size
        size++;

        // while loop to look for an index to insert "e"
        while(probeCount < size){
            // calculate the index of table to check
            int index = probeIndex(e.hashCode(), probeCount, table.length);

            // if table[index] equals null, then that is an index that "e" can be inserted into
            if(table[index] == null){
                // insert "e" into the correct index of table
                table[index] = e;
                return true;
            }
            // if table[index] equals REMOVED, then that is an index that "e" can be inserted into
            else if(table[index].equals(REMOVED)){
                // insert "e" into the correct index of table
                table[index] = e;
                // decrement numRemoved because an instance of REMOVED is being overwritten and replaced
                numRemoved--;
                return true;
            }
            // increment probeCount to prepare to probe a new index
            probeCount++;
        }

        // if these lines are reached, then "e" was not able to be inserted into table,
            // so decrement size and return false
        size--;
        return false;
    }

    // method to resize and rehash table
    @SuppressWarnings("unchecked")
    private void rehash() {
        // TO DO
        // increment nextTableSizeIndex so the next table size value can be reached in tableSizes
        nextTableSizeIndex++;
        // create a second Object array to store the current version of table
        Object[] tableCopy = table;
        // make a table a new Object array with the size specified by the next value inside tableSizes
        table = new Object[tableSizes[nextTableSizeIndex]];
        // reset size to 0
        size = 0;
        // reset numRemoved
        numRemoved = 0;

        // for loop to add all the non-null and non-REMOVED elements back into table
        for(int i = 0; i < tableCopy.length; i++){
            if(tableCopy[i] != null && !tableCopy.equals(REMOVED)){
                add((E)tableCopy[i]);
            }
        }

        // calculate and set the new threshold
        thresholdSize = (int) Math.ceil(table.length * maxLoadFactor);
    }

    // method to delete an element from table
    public boolean remove(E e) {
        // TO DO
        // if "e" is not inside table, then it doesn't exist in table to delete from table
        if(!contains(e)){
            return false;
        }

        // declare and initialize probeCount to 0
        int probeCount = 0;

        // while loop to go through table looking for "e" in order to remove "e" from table
        while(probeCount < size){
            // calculate the index of table to check
            int index = probeIndex(e.hashCode(), probeCount, table.length);

            // if table[index] equals null, return false, because "e" or REMOVED should have been at this index
            if(table[index] == null){
                return false;
            }
            // if "e" was found at table[index], replace it with REMOVED,
                // increment numRemoved, decrement size and return true
            else if(e.equals(table[index])){
                size--;
                numRemoved++;
                table[index] = REMOVED;
                return true;
            }
            // increment probeCount to prepare to probe a new index
            probeCount++;
        }

        // if this instruction is reached, "e" was not able to be removed from table, so return false
        return false;
    }

    // method to return whether table is currently empty of actual elements (REMOVED is not factored in)
    public boolean isEmpty() {
        // TO DO
        return size == 0;
    }

    // method to return the number of actual element inside table
    public int size() {
        // TO DO
        return size;
    }

    // This program is not implementing an iterator
    public java.util.Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }
}
