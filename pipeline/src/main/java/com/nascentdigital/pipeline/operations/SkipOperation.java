package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class SkipOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final int _count;

    // endregion


    // region constructors

    public SkipOperation(Iterable<TElement> source, int count) {
        _source = source;
        _count = count;
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

        private final java.util.Iterator<TElement> _input = _source.iterator();


        Iterator() {
            int offset = 0;
            while (offset++ < _count
                    && _input.hasNext()) {
                _input.next();
            }
        }

        @Override
        public boolean hasNext() {
            return _input.hasNext();
        }

        @Override
        public TElement next() { return _input.next(); }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}