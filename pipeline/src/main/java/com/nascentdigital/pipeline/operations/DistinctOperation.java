package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;

import java.util.HashSet;
import java.util.Set;


public class DistinctOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;

    // endregion


    // region constructors

    public DistinctOperation(Iterable<TElement> source) {
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

        private final java.util.Iterator<TElement> _input = _source.iterator();
        private final Set<TElement> _distinctElements = new HashSet<>();
        private Boolean _hasNext;
        private TElement _next;

        @Override
        public boolean hasNext() {

            // get next if isn't undefined
            if (_hasNext == null) {

                // assume that there is nothing left
                _hasNext = false;

                // using underlying iterator until an element
                while (_input.hasNext()) {

                    // get element
                    TElement element = _input.next();

                    // use element if it is unique
                    if (_distinctElements.add(element)) {

                        // set state
                        _hasNext = true;
                        _next = element;

                        // stop processing
                        break;
                    }
                }
            }

            // return value
            return _hasNext;
        }

        @Override
        public TElement next() {

            // get item
            TElement next = _next;

            // clear state
            _hasNext = null;
            _next = null;

            // return item
            return next;
        }
    }

    // endregion
}
