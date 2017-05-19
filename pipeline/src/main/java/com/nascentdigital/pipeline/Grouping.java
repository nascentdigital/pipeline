package com.nascentdigital.pipeline;


import java.util.Iterator;

public class Grouping<TKey, TElement> implements Iterable<TElement> {

    // region instance variables

    public final TKey key;

    protected final Iterable<TElement> elements;

    // endregion


    // region constructors

    public Grouping(TKey key, Iterable<TElement> elements) {
        this.key = key;
        this.elements = elements;
    }

    // endregion

    // region Iterable<TElement> interface

    @Override
    public Iterator<TElement> iterator() {
        return elements.iterator();
    }

    // endregion
}
