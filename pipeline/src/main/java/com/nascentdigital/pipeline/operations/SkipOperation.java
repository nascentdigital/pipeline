package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.PipelineOperation;


public class SkipOperation<TElement> implements PipelineOperation<TElement> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final int _count;

    // endregion


    // region constructors

    public SkipOperation(Iterable<TElement> source, int count) {
        _source = source;
        _count = count;
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


        @Override
        public boolean hasNext() {

            // skip on first invocation
            if (!_skipped) {

                // flush out first "count" items from iterator, or stop if input is empty
                int offset = 0;
                while (offset++ < _count
                        && _input.hasNext()) {
                    _input.next();
                }

                // mark skipped
                _skipped = true;
            }

            // use underlying input
            return _input.hasNext();
        }

        @Override
        public TElement next() { return _input.next(); }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    // endregion
}
