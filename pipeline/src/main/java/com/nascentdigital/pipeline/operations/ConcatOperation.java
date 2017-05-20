package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class ConcatOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Iterable<TElement> _addition;

    // endregion


    // region constructors

    public ConcatOperation(Iterable<TElement> source, Iterable<TElement> addition) {
        _source = source;
        _addition = addition;
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
        private final java.util.Iterator<TElement> _addition =
                ConcatOperation.this._addition.iterator();
        private boolean _concatenating;

        @Override
        public boolean hasNext() {

            // use addition if concatenating
            if (_concatenating) {
                return _addition.hasNext();
            }

            // or try to use input
            boolean hasNext = _input.hasNext();

            // begin concatenating if base input is empty
            if (!hasNext) {
                _concatenating = true;

                hasNext = _addition.hasNext();
            }

            // return next state
            return hasNext;
        }

        @Override
        public TElement next() {
            return _concatenating
                    ? _addition.next()
                    : _input.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}
