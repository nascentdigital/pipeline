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

            // compare _addition sequence's element to elements seen in history
            // return once if they intersect - or end if sequence and/or history is empty
            while (_addition.hasNext() && !history.isEmpty()) {
                _nextElement = _addition.next();

                // if history already contains element, it intersects
                if (history.contains(_nextElement)) {

                    // remove element from history to avoid double counting
                    history.remove(_nextElement);
                    return true;
                }
            }

            // return (reaches here if no next intersecting element in additional)
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

        // endregion

    }

    // endregion
}
