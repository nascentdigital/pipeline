package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;


public class TakeWhileOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Predicate<TElement> _predicate;

    // endregion


    // region constructors

    public TakeWhileOperation(Iterable<TElement> source, Predicate<TElement> predicate) {
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
        private Boolean _hasElement;
        private TElement _element;

        @Override
        public boolean hasNext() {

            // return immediately if already evaluated
            if (_hasElement != null) {
                return _hasElement;
            }

            // evaluate predicate, if there is an element
            TElement element = null;
            _hasElement = _input.hasNext()
                    && _predicate.evaluate(element = _input.next());

            // capture element if predicate passes
            if (_hasElement) {
                _element = element;
            }

            // return result of evaluation
            return _hasElement;
        }

        @Override
        public TElement next() {

            // capture element
            TElement element = _element;

            // reset state
            _hasElement = null;
            _element = null;

            // return element
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}