package hash;

// This program tests the correctness of the following two classes:
//     MyHashSet (from Y. Daniel Liang's Introduction to Java Programming, Comprehensive Version)
//     MyQuadraticHashSet
// The tests involve many add, remove, and contains operations on random integers.
// The results are compared to the results of performing the same operations on a java.util.HashSet.

import java.util.*;

public class TestHashSets {
    private enum OperationType {
        ADD, CONTAINS, REMOVE
    }

    static final int NUM_VALUES = 1000000;
    static final int BOUND = NUM_VALUES;
    static Random rand = new Random(100);

    public static void main(String args[]) {
        // The following prime numbers are intended to be used
        // as table sizes in hashing with quadratic probing.
        final int[] primeSizes = { 17, 37, 79, 163, 331, 673, 1361, 2729, 5471, 10949, 21911, 43853, 87719, 175447,
                350899, 701819, 1403641, 2807303, 5614657, 11229331, 22458671 };

        System.out.println("Starting tests.");
        HashSet<Integer> javaLibHashSet = new HashSet<Integer>();
        MyHashSet<Integer> textbookHashSet = new MyHashSet<Integer>();
        MyQuadraticHashSet<Integer> quadHashSetLowLoad = new MyQuadraticHashSet<Integer>(0.10, primeSizes);
        MyQuadraticHashSet<Integer> quadHashSetHighLoad = new MyQuadraticHashSet<Integer>(0.50, primeSizes);

        System.out.println("Add operations ...");
        performTests(javaLibHashSet, textbookHashSet, quadHashSetLowLoad, quadHashSetHighLoad, OperationType.ADD);
        checkSizes(javaLibHashSet, textbookHashSet, quadHashSetLowLoad, quadHashSetHighLoad);

        System.out.println("Contains operations ...");
        performTests(javaLibHashSet, textbookHashSet, quadHashSetLowLoad, quadHashSetHighLoad, OperationType.CONTAINS);

        System.out.println("Remove operations ...");
        performTests(javaLibHashSet, textbookHashSet, quadHashSetLowLoad, quadHashSetHighLoad, OperationType.REMOVE);
        checkSizes(javaLibHashSet, textbookHashSet, quadHashSetLowLoad, quadHashSetHighLoad);

        System.out.println("Tests successfully completed.");
        System.exit(0);
    }

    static void performTests(HashSet<Integer> javaLibHashSet, MyHashSet<Integer> textbookHashSet,
                             MyQuadraticHashSet<Integer> quadHashSetLowLoad, MyQuadraticHashSet<Integer> quadHashSetHighLoad,
                             OperationType operationType) {
        for (int i = 0; i < NUM_VALUES; i++) {
            int value = rand.nextInt(BOUND) - BOUND / 2;
            boolean javaLibReturnVal;
            boolean textbookReturnVal;
            boolean quadLowLoadReturnVal;
            boolean quadHighLoadReturnVal;
            switch (operationType) {
                case ADD:
                    javaLibReturnVal = javaLibHashSet.add(value);
                    textbookReturnVal = textbookHashSet.add(value);
                    quadLowLoadReturnVal = quadHashSetLowLoad.add(value);
                    quadHighLoadReturnVal = quadHashSetHighLoad.add(value);
                    break;
                case CONTAINS:
                    javaLibReturnVal = javaLibHashSet.contains(value);
                    textbookReturnVal = textbookHashSet.contains(value);
                    quadLowLoadReturnVal = quadHashSetLowLoad.contains(value);
                    quadHighLoadReturnVal = quadHashSetHighLoad.contains(value);
                    break;
                default: // REMOVE
                    javaLibReturnVal = javaLibHashSet.remove(value);
                    textbookReturnVal = textbookHashSet.remove(value);
                    quadLowLoadReturnVal = quadHashSetLowLoad.remove(value);
                    quadHighLoadReturnVal = quadHashSetHighLoad.remove(value);
            }
            if (javaLibReturnVal != textbookReturnVal || javaLibReturnVal != quadLowLoadReturnVal
                    || javaLibReturnVal != quadHighLoadReturnVal) {
                System.out.println("Error:");
                System.out.println("On value: " + value);
                System.out.println("The JCF HashSet returned " + javaLibReturnVal);
                System.out.println("The MyHashSet returned " + textbookReturnVal);
                System.out.println("The .10 load threshold MyQuadraticHashSet returned " + quadLowLoadReturnVal);
                System.out.println("The .50 load threshold MyQuadraticHashSet returned " + quadHighLoadReturnVal);
                System.exit(1);
            }
        }
    }

    static void checkSizes(HashSet<Integer> jcfHashSet, MyHashSet<Integer> liangHashSet,
                           MyQuadraticHashSet<Integer> lowLoadSet, MyQuadraticHashSet<Integer> highLoadSet) {
        int size = jcfHashSet.size();
        System.out.println("Set size:  " + size);

        if (size != liangHashSet.size() || size != lowLoadSet.size() || size != highLoadSet.size()) {
            System.out.println("Error:");
            System.out.println("The JCF HashSet has size " + size);
            System.out.println("The MyHashSet has size " + liangHashSet.size());
            System.out.println("The .10 load threshold MyQuadraticHashSet has size " + lowLoadSet.size());
            System.out.println("The .50 load threshold MyQuadraticHashSet has size " + highLoadSet.size());
            System.exit(1);
        }
    }
}
