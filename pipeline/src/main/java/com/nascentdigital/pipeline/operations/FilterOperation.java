package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;


public class FilterOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Predicate<TElement> _filter;

    // endregion


    // region constructors

    public FilterOperation(Iterable<TElement> source, Predicate<TElement> filter) {
        _source = source;
        _filter = filter;
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

                    // use element if it passes filter
                    if (_filter.predicate(element)) {

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
