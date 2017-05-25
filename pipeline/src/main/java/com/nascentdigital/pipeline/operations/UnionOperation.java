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

        // saving what we've seen previously
        private HashSet<TElement> history = new HashSet<>();

        // hold next element
        private TElement _nextElement;

        // answers: Does "union" have next element?
        @Override
        public boolean hasNext() {

            // start with _input sequence
            // iterate till you find next unique element
            while (_input.hasNext()) {
                // store next element in variable
                _nextElement = _input.next();

                // check history to confirm you have not seen this element before
                if (!history.contains(_nextElement)) {
                    // add element to "seen" history set
                    history.add(_nextElement);

                    // return once we've seen a unique element to add to the union
                    return true;
                }
            }

            // finished with input sequence, so no longer looking at input, but now using _addition
            // return true for hasNext() if there is a unique element in the _addition sequence
            // iterate till you find next unique element
            while (_addition.hasNext()) {
                // store next element in variable
                _nextElement = _addition.next();

                // check history to confirm you have not seen this element before
                if (!history.contains(_nextElement)) {
                    // add element to "seen" history set
                    history.add(_nextElement);

                    // return once we've seen a unique element to add to the union
                    return true;
                }
            }

            // if it reaches here, return false
            return false;
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

        /*
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

        */

    }

    // endregion
}
