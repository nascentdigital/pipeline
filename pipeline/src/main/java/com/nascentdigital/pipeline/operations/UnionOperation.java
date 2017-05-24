package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;

import java.util.HashSet;


public class UnionOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Iterable<TElement> _addition;

    // endregion


    // region constructors

    public UnionOperation(Iterable<TElement> source, Iterable<TElement> addition) {
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
                UnionOperation.this._addition.iterator();

        // return value for hasNext() call
        private boolean hasNext;

        // flag for if working on input vs additional sequence
        private boolean _inputSeq = true;

        // saving what we've seen previously
        private HashSet<TElement> history = new HashSet<>();

        // hold next element
        private TElement _nextElement;

        @Override
        public boolean hasNext() {

            // using input sequence
            if (_inputSeq) {
                // add input's next element to history and return true for hasNext()
                if (returnNext(_input)) {
                    return hasNext;
                }
            }

            // finished with input sequence, now using additional
            _inputSeq = false;

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
            while (sequenceSource.hasNext()) {
                _nextElement = sequenceSource.next();

                if (!history.contains(_nextElement)) {

                    hasNext = true;
                    history.add(_nextElement);
                    return true;
                }
            }
            return false;
        }

        // endregion

    }

    // endregion
}
