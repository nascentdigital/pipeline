package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Selector;


public class FlatProjectionOperation<TInput, TOutput> implements PipelineOperation<TOutput> {

    // region instance variables

    private final Iterable<TInput> _source;
    private final Selector<TInput, Iterable<TOutput>> _selector;

    // endregion


    // region constructors

    public FlatProjectionOperation(Iterable<TInput> source, Selector<TInput,
            Iterable<TOutput>> selector) {
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
        private java.util.Iterator<TOutput> _output;

        @Override
        public boolean hasNext() {

            // return immediately if the output has a value ready
            if (_output != null && _output.hasNext()) {
                return true;
            }

            // or find the next output
            while (_input.hasNext()) {

                // update the output
                _output = _selector.select(_input.next()).iterator();

                // stop processing if the output has elements
                if (_output != null
                        && _output.hasNext()) {
                    return true;
                }
            }

            // assume there's nothing left if this point is reached
            return false;
        }

        @Override
        public TOutput next() {
            return _output.next();
        }
    }

    // endregion
}
