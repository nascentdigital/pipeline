package com.nascentdigital.pipeline.operations;

import java.util.Iterator;


/**
 * A helper iterator that always returns false.
 */
class EmptyIterator implements Iterator {

    // region static variables

    static final EmptyIterator instance = new EmptyIterator();

    // endregion


    // region constructors

    private EmptyIterator() {
    }

    // endregion


    // region Iterator interface

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    // endregion
}
