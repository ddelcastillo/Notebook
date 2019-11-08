package UnionFinder;

import java.util.Hashtable;

// TODO Documentations.

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

    // TODO implementation given an array K[].
    // TODO implementation given an UnionFinder (shadow copy).

    // Methods

    public int root(K pBox)
    {
        int index = keyToNumber.get(pBox);
        return numUnionFinder.root(index);
    }

    // TODO root checked.

    public void merge(K pBox1, K pBox2)
    {
        int index1 = keyToNumber.get(pBox1);
        int index2 = keyToNumber.get(pBox2);
        numUnionFinder.merge(index1, index2);
    }

    // TODO merge checked.

    // Extra methods

    public void add(K pBox)
    {
        keyToNumber.put(pBox, N);
        numberToKey.put(N, pBox);
        numUnionFinder.add(N);
        ++N;
    }

    // TODO add checked.

    public int size(K pBox)
    {
        int index = keyToNumber.get(pBox);
        return numUnionFinder.size(index);
    }

    // TODO size checked.

    public int totalRoots()
    { return numUnionFinder.totalRoots(); }

    // TODO par.

    // TODO toString.
}
