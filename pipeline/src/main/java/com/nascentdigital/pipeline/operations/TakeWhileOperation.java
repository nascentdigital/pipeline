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
        private TElement _nextElement;

        @Override
        public boolean hasNext() {

            if (_input.hasNext()) {

                // get the element
                TElement element = _input.next();

                // mark as done if predicate fails
                if (!_predicate.evaluate(element)) {
                    return false;
                }else {
                    _nextElement = element;
                    return true;
                }
            }else
            {
                return false;
            }
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
    }

    // endregion
}
