package com.nascentdigital.pipeline.operations;

import com.nascentdigital.pipeline.Grouping;
import com.nascentdigital.pipeline.PipelineOperation;
import com.nascentdigital.pipeline.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GroupByOperation<TElement, TKey> implements PipelineOperation<Grouping<TKey, TElement>> {

    // region instance variables

    private final Iterable<TElement> _source;
    private final Selector<TElement, TKey> _selector;

    // endregion


    // region constructors

    public GroupByOperation(Iterable<TElement> source, Selector<TElement, TKey> selector) {
        _source = source;
        _selector = selector;
    }

    // endregion


    // region Iterable<TElement> interface

    @Override
    public Iterator<Grouping<TKey, TElement>> iterator() {

        // iterate through all elements to group them
        Map<TKey, List<TElement>> groups = new HashMap<>();
        for (TElement element : _source) {

            // get key for element
            TKey key = _selector.select(element);

            // get group, or create one
            List<TElement> group = groups.get(key);
            if (group == null) {

                // create new group
                group = new ArrayList<>();

                // persist group
                groups.put(key, group);
            }

            // add item to group
            group.add(element);
        }

        // create groupings
        List<Grouping<TKey, TElement>> groupings = new ArrayList<>();

        // create groupings for groups
        for (Map.Entry<TKey, List<TElement>> groupEntry : groups.entrySet()) {

            // create grouping
            Grouping<TKey, TElement> grouping = new Grouping<>(groupEntry.getKey(),
                    groupEntry.getValue());

            // and add to group
            groupings.add(grouping);
        }

        // use groupings as iterator
        return groupings.iterator();
    }

    // endregion
}
