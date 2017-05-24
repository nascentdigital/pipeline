package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;

import java.util.HashSet;


public class IntersectOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Iterable<TElement> _addition;

    // endregion


    // region constructors

    public IntersectOperation(Iterable<TElement> source, Iterable<TElement> addition) {
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
                IntersectOperation.this._addition.iterator();

        // return value for hasNext() call
        private boolean hasNext;

        // saving what we've seen previously
        private HashSet<TElement> history = new HashSet<>();

        // hold next element
        private TElement _nextElement;

        Iterator() {
            while (_input.hasNext()) {

                history.add(_input.next());
            }
        }

        @Override
        public boolean hasNext() {

            // reset hasNext
            hasNext = false;

            // return true for hasNext() if there is any unique element left in the sequence
            if (returnNext(_addition)) {
                return hasNext;
            }

            // return (reaches here if no next unique element in additional)
            return hasNext;
        }

        @Override
        public TElement next() {
            TElement element = _nextElement;
            _nextElement = null;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }

        // region helper method
        private boolean returnNext(java.util.Iterator<TElement> sequenceSource) {
            while (sequenceSource.hasNext() && !history.isEmpty()) {
                _nextElement = sequenceSource.next();

                if (history.contains(_nextElement)) {

                    hasNext = true;
                    history.remove(_nextElement);
                    return true;
                }
            }
            return false;
        }

        // endregion

    }

    // endregion
}
