package UnionFinder;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

// TODO Documentations.
// TODO tests.

public class UnionFinder<K> implements IUnionFinder<K>
{
    // Attributes

    private int N;

    private ExpandableNumUnionFinder numUnionFinder;

    private Hashtable<K, Integer> keyToNumber;

    private Hashtable<Integer, K> numberToKey;

    // Constructor

    public UnionFinder()
    {
        numUnionFinder = new ExpandableNumUnionFinder();
        keyToNumber = new Hashtable<>();
        numberToKey = new Hashtable<>();
        N = 0;
    }

    public UnionFinder(K[] pBoxes)
    {
        numUnionFinder = new ExpandableNumUnionFinder(pBoxes.length);
        keyToNumber = new Hashtable<>(pBoxes.length);
        numberToKey = new Hashtable<>(pBoxes.length);
        for(K box : pBoxes)
        {
            keyToNumber.put(box, N);
            numberToKey.put(N, box);
            ++N;
        }
    }

    public UnionFinder(Collection<K> pBoxes)
    {
        numUnionFinder = new ExpandableNumUnionFinder(pBoxes.size());
        keyToNumber = new Hashtable<>(pBoxes.size());
        numberToKey = new Hashtable<>(pBoxes.size());
        for(K box : pBoxes)
        {
            keyToNumber.put(box, N);
            numberToKey.put(N, box);
            ++N;
        }
    }

    public UnionFinder(UnionFinder pUnionFinder)
    {
        this.numUnionFinder = new ExpandableNumUnionFinder(pUnionFinder.numUnionFinder);
        this.keyToNumber = new Hashtable<>(pUnionFinder.totalRoots());
        this.numberToKey = new Hashtable<>(pUnionFinder.totalRoots());
        Iterator<K> iterator = pUnionFinder.keyToNumber.keySet().iterator();
        K key;
        int value;
        while(iterator.hasNext())
        {
            key = iterator.next();
            value = (int) pUnionFinder.keyToNumber.get(key);
            this.keyToNumber.put(key, value);
            this.numberToKey.put(value, key);
        }
    }

    // Methods

    public int root(K pBox)
    {
        int index = keyToNumber.get(pBox);
        return numUnionFinder.root(index);
    }

    public Integer rootChecked(K pBox)
    { return keyToNumber.containsKey(pBox) ? numUnionFinder.root(keyToNumber.get(pBox)) : null; }

    public void merge(K pBox1, K pBox2)
    { numUnionFinder.merge(keyToNumber.get(pBox1), keyToNumber.get(pBox2)); }

    public void mergeChecked(K pBox1, K pBox2)
    {
        if(keyToNumber.containsKey(pBox1) && keyToNumber.containsKey(pBox2))
            merge(pBox1, pBox2);
    }

    // Extra methods

    public void add(K pBox)
    {
        keyToNumber.put(pBox, N);
        numberToKey.put(N, pBox);
        numUnionFinder.add(N);
        ++N;
    }

    public void addChecked(K pBox)
    {
        if(keyToNumber.containsKey(pBox))
            return;
        add(pBox);
    }

    public int size(K pBox)
    { return numUnionFinder.size(keyToNumber.get(pBox)); }

    public Integer sizeChecked(K pBox)
    { return keyToNumber.containsKey(pBox) ? size(pBox) : null; }

    public int totalRoots()
    { return numUnionFinder.totalRoots(); }

    public Hashtable<K, Integer> par()
    {
        Hashtable<K, Integer> par = new Hashtable<>(N);
        for(K box : keyToNumber.keySet())
            par.put(box, numUnionFinder.root(keyToNumber.get(box)));
        return par;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("Size: " + N + "\n");
        for(K box : keyToNumber.keySet())
            sb.append(box.toString()).append(": ").append(keyToNumber.get(box)).append("\n");
        return sb.toString();
    }
}
