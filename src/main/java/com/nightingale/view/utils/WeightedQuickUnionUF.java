package com.nightingale.view.utils;

/**
 * Weighted quick-union (without path compression).
 *  <p><i>Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne.</i>
 */

public class WeightedQuickUnionUF {
    /**
     * id[i] = parent of i
     */
    private int[] id;
    /**
     * sz[i] = number of objects in subtree rooted at i
     */
    private int[] sz;
    /**
     * number of components
     */
    private int count;

    /**
     * Create an empty union find data structure with N isolated sets.
     */
    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    /**
     * @return the number of disjoint sets.
     */
    public int count() {
        return count;
    }

    /**
     * @return component identifier for component containing p
     */
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    //

    /**
     * @return Are objects p and q in the same set?
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Replace sets containing p and q with their union.
     */
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // make smaller root point to larger one
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }
}

