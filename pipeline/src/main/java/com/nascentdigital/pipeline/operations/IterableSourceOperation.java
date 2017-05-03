package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class IterableSourceOperation<TElement> implements PipelineOperation<TElement> {

    // region instance methods

    private final Iterable<TElement> _input;

    // endregion


    // region constructors

    public IterableSourceOperation(Iterable<TElement> input) {
        _input = input;
    }

    // endregion


    // region Iterable<TElement> interface

    @SuppressWarnings("unchecked")
    @Override
    public java.util.Iterator<TElement> iterator() {

        // always return empty iterator if iterable is null
        if (_input == null) {
            return EmptyIterator.instance;
        }

        // or return iterator
        return new Iterator();
    }

    // endregion


    // region internal classes

    private class Iterator implements java.util.Iterator<TElement> {

        private final java.util.Iterator<TElement> _iterator = _input.iterator();

        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }

        @Override
        public TElement next() {
            return _iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}
