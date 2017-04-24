package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;


public class TakeOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final int _limit;

    // endregion


    // region constructors

    public TakeOperation(Iterable<TElement> source, int limit) {
        _source = source;
        _limit = limit;
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
        private int _count;


        @Override
        public boolean hasNext() {

            // mark as done if short circuit if limit is reached, or iterator is done
            if (_count >= _limit
                    || !_input.hasNext()) {
                return false;
            }

            // increment count
            ++_count;

            // indicate more
            return true;
        }

        @Override
        public TElement next() { return _input.next(); }
    }

    // endregion
}
