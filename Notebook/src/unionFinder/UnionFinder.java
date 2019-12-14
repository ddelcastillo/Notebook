// @formatter:off
package unionFinder;

import util.Checked;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class that represents a generic union finder of boxes with type K.
 * @author Daniel del Castillo A. https://github.com/ddelcastillo
 * Class finished and corrected as of 14/12/19.
 */
@Checked
(note = "Methods with the 'Checked' signature enforce additional checks to avoid errors and to " +
"ensure the structure's correctness in exchange of efficiency. For fastest results, use " +
"the non 'Checked' methods, however, these are liable to: NullPointer exceptions, key-value " +
"mismatches in the corresponding hashtable and wrong tracking of the number of boxes.")
public class UnionFinder<T> implements IUnionFinder<T>
{
    // Attributes

    /**
     * The counter to assign boxes in the numerical union finder.
     */
    private int N;

    /**
     * The numerical union finder that manages the boxes with their assigned numbers.
     */
    private ExpandableBasicUnionFinder numUnionFinder;

    /**
     * The map that accesses the given number to a certain box.
     */
    private HashMap<T, Integer> boxToNumber;

    /**
     * The map that accesses the assigned box of a given number.
     */
    private HashMap<Integer, T> numberToBox;

    // Constructors

    /**
     * Creates an UnionFinder object.
     */
    public UnionFinder()
    {
        numUnionFinder = new ExpandableBasicUnionFinder();
        boxToNumber = new HashMap<>();
        numberToBox = new HashMap<>();
        N = 0;
    }

    /**
     * Creates an ExpandableUnionFinder object with the given boxes.
     * @param pBoxes The objects of the boxes to add to the union finder.
     */
    public UnionFinder(T[] pBoxes)
    {
        numUnionFinder = new ExpandableBasicUnionFinder(pBoxes.length);
        boxToNumber = new HashMap<>(pBoxes.length);
        numberToBox = new HashMap<>(pBoxes.length);
        for(T box : pBoxes)
        {
            boxToNumber.put(box, N);
            numberToBox.put(N, box);
            ++N;
        }
    }

    /**
     * Creates an ExpandableUnionFinder object with the given boxes.
     * @param pBoxes The objects of the boxes to add to the union finder.
     */
    public UnionFinder(Collection<T> pBoxes)
    {
        numUnionFinder = new ExpandableBasicUnionFinder(pBoxes.size());
        boxToNumber = new HashMap<>(pBoxes.size());
        numberToBox = new HashMap<>(pBoxes.size());
        for(T box : pBoxes)
        {
            boxToNumber.put(box, N);
            numberToBox.put(N, box);
            ++N;
        }
    }

    /**
     * Creates an UnionFinder object copy of the given union finder.
     * @param pUnionFinder The union finder to copy.
     */
    public UnionFinder(UnionFinder pUnionFinder)
    {
        this.numUnionFinder = new ExpandableBasicUnionFinder(pUnionFinder.numUnionFinder);
        this.boxToNumber = new HashMap<>(pUnionFinder.totalRoots());
        this.numberToBox = new HashMap<>(pUnionFinder.totalRoots());
        Iterator<T> iterator = pUnionFinder.boxToNumber.keySet().iterator();
        T key;
        int value;
        while(iterator.hasNext())
        {
            key = iterator.next();
            value = (int) pUnionFinder.boxToNumber.get(key);
            this.boxToNumber.put(key, value);
            this.numberToBox.put(value, key);
        }
    }

    // Methods

    /**
     * Doesn't check if pBox is a valid box. For this, use rootChecked.
     * Finds the root of the given box.
     * @param pBox The box.
     * @return The root of the box.
     */
    public T root(T pBox)
    { return numberToBox.get(numUnionFinder.root(boxToNumber.get(pBox))); }

    /**
     * Finds the root of the given box. Checks that pBox is a valid box.
     * @param pBox The box.
     * @return The root of the box or {@code null} if the box doesn't exist.
     */
    public T rootChecked(T pBox)
    { return boxToNumber.containsKey(pBox) ? root(pBox) : null; }

    /**
     * Doesn't check if boxes pBox1 and pBox2 are {@code null} or exist. For this, use mergeChecked.
     * Merges the two boxes.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void merge(T pBox1, T pBox2)
    { numUnionFinder.merge(boxToNumber.get(pBox1), boxToNumber.get(pBox2)); }

    /**
     * Checks that both boxes pBox1 and pBox2 are not {@code null} and exist.
     * Merges the two boxes if they both are not {@code null} and exist.
     * @param pBox1 The first box.
     * @param pBox2 The second box.
     */
    public void mergeChecked(T pBox1, T pBox2)
    {
        if(pBox1 != null && pBox2 != null && boxToNumber.containsKey(pBox1) && boxToNumber.containsKey(pBox2))
            merge(pBox1, pBox2);
    }

    // Extra methods

    /**
     * Doesn't check if pBox is {@code null} or already exists. For this, use addChecked.
     * Adds a box.
     * @param pBox The box to add.
     */
    public void add(T pBox)
    {
        boxToNumber.put(pBox, N);
        numberToBox.put(N, pBox);
        numUnionFinder.add(N);
        ++N;
    }

    /**
     * Checks if pBox is not {@code null} and already exists.
     * Adds a box if it's not {@code null} and doesn't already exist.
     * @param pBox The box to add.
     */
    public void addChecked(T pBox)
    {
        if(boxToNumber.containsKey(pBox))
            return;
        add(pBox);
    }

    /**
     * Doesn't check if pBox is null or if it exists. For this, use sizeChecked.
     * Returns either the size of the box if it's not connected or the size of the union if it is.
     * @param pBox The box.
     * @return The size of the box or the union it belongs to.
     */
    public int size(T pBox)
    { return numUnionFinder.size(boxToNumber.get(pBox)); }

    /**
     * Checks if pBox is not null and exists.
     * Returns either the size of the box if it's not connected, the size of the union if it is,
     * or {@code null} if the box is null or doesn't exist.
     * @param pBox The box.
     * @return The size of the box or the union it belongs to, or {@code null} if the box is null or doesn't exist.
     */
    public Integer sizeChecked(T pBox)
    { return pBox != null && boxToNumber.containsKey(pBox) ? size(pBox) : null; }

    /**
     * @return The number of boxes that are not in union and super-boxes.
     */
    public int totalRoots()
    { return numUnionFinder.totalRoots(); }

    /**
     * @return The HashMap with the parents of each box.
     */
    public HashMap<T, T> parents()
    {
        HashMap<T, T> par = new HashMap<>(N);
        for(T box : boxToNumber.keySet())
            par.put(box, numberToBox.get(numUnionFinder.root(boxToNumber.get(box))));
        return par;
    }

    /**
     * Transcripts the union finder's contents into a String.
     * @return The String with the union finder's contents.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Size : " + N + "\n");
        for(T box : boxToNumber.keySet())
            sb.append(box.toString()).append(" : ").append(boxToNumber.get(box)).append("\n");
        return sb.toString();
    }
}