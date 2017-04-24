package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class IterableSourceOperation<TElement> implements PipelineOperation<TElement> {

    // region instance methods

    private final Iterable<TElement> _ipnut;

    // endregion


    // region constructors

    public IterableSourceOperation(Iterable<TElement> ipnut) {
        _ipnut = ipnut;
    }

    // endregion


    // region Iterable<TElement> interface

    @Override
    public Iterator iterator() {
        return new Iterator();
    }

    // endregion


    // region internal classes

    private class Iterator implements java.util.Iterator<TElement> {

        private final java.util.Iterator<TElement> _iterator = _ipnut.iterator();

        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }

        @Override
        public TElement next() {
            return _iterator.next();
        }
    }

    // endregion
}
