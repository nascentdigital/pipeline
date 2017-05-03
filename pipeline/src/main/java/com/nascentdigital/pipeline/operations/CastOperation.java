package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Predicate;


public class CastOperation<TInput, TOutput> implements PipelineOperation<TOutput> {

    // region instance variables

    private final Iterable<TInput> _source;
    private final Class<TOutput> _targetClass;

    // endregion


    // region constructors

    public CastOperation(Iterable<TInput> source, Class<TOutput> targetClass) {
        _source = source;
        _targetClass = targetClass;
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
        private Boolean _hasNext;

        @Override
        public boolean hasNext() {
            return _input.hasNext();
        }

        @SuppressWarnings("unchecked")
        @Override
        public TOutput next() {

            // throw if type mismatches
            TInput element = _input.next();
            if (element != null
                    && !_targetClass.isInstance(element)) {
                throw new ClassCastException("Element cannot be casted to \""
                        + _targetClass.getSimpleName()
                        + "\": "
                        + element.getClass().getSimpleName());
            }

            // return element
            return (TOutput)element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}
