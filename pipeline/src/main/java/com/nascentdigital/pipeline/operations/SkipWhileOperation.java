package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;


public class SkipWhileOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Predicate<TElement> _predicate;

    // endregion


    // region constructors

    public SkipWhileOperation(Iterable<TElement> source, Predicate<TElement> predicate) {
        _source = source;
        _predicate = predicate;
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
        private TElement _firstElement;
        private boolean _first;

        Iterator() {
            while (_input.hasNext()) {

                // get the element
                TElement element = _input.next();

                // stop if predicate fails
                if (!_predicate.evaluate(element)) {

                    // capture first
                    _firstElement = element;
                    _first = true;

                    // stop processing
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return _first
                    || _input.hasNext();
        }

        @Override
        public TElement next() {

            // return first, if any
            final TElement element;
            if (_first) {

                // capture first
                element = _firstElement;

                // clear first
                _firstElement = null;
                _first = false;
            }

            // return next
            else {
                element = _input.next();
            }

            // return element
            return  element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}