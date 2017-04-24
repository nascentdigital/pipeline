package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Selector;


public class ProjectionOperation<TInput, TOutput> implements PipelineOperation<TOutput> {

    // region instance variables

    private final Iterable<TInput> _source;
    private final Selector<TInput, TOutput> _selector;

    // endregion


    // region constructors

    public ProjectionOperation(Iterable<TInput> source, Selector<TInput, TOutput> selector) {
        _source = source;
        _selector = selector;
    }

    // endregion


    // region Iterable<TElement> interface

    @Override
    public Iterator iterator() {
        return new Iterator();
    }

    // endregion


    // region internal classes

    private class Iterator implements java.util.Iterator<TOutput> {

        private final java.util.Iterator<TInput> _input = _source.iterator();

        @Override
        public boolean hasNext() {
            return _input.hasNext();
        }

        @Override
        public TOutput next() {
            return _selector.select(_input.next());
        }
    }

    // endregion
}
