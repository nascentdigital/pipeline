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
        private boolean _skipped;
        private TElement _first;
        private boolean _hasFirst;


        @Override
        public boolean hasNext() {

            // skip on first invocation
            if (!_skipped) {

                // mark skipped
                _skipped = true;

                // skip while element passes condition, or until sequence is exhausted
                while (_input.hasNext()) {

                    // get next element
                    TElement element = _input.next();

                    // stop if element fails condition
                    if (!_predicate.predicate(element)) {

                        // capture next value
                        _first = element;
                        _hasFirst = true;

                        // break loop
                        break;
                    }
                }
            }

            // use underlying input
            return _hasFirst
                    || _input.hasNext();
        }

        @Override
        public TElement next() {

            // return the first element if it's still valid
            final TElement element;
            if (_hasFirst) {

                // flag as invalid
                _hasFirst = false;

                // capture and clear element
                element = _first;
                _first = null;
            }

            // or use iterator
            else {
                element = _input.next();
            }

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
