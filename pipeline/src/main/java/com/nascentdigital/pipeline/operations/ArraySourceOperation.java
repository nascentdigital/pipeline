package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class ArraySourceOperation<TElement> implements PipelineOperation<TElement> {

    // region instance methods

    private final TElement[] _source;

    // endregion


    // region constructors

    public ArraySourceOperation(TElement[] source) {
        _source = source;
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

        private int _offset;

        @Override
        public boolean hasNext() {
            return _offset < _source.length;
        }

        @Override
        public TElement next() {
            return _source[_offset++];
        }
    }

    // endregion
}
