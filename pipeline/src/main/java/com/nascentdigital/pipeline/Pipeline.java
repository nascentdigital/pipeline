package com.nascentdigital.pipeline;


import com.nascentdigital.pipeline.annotations.Group;
import com.nascentdigital.pipeline.annotations.GroupType;
import com.nascentdigital.pipeline.operations.ArraySourceOperation;
import com.nascentdigital.pipeline.operations.CastOperation;
import com.nascentdigital.pipeline.operations.ConcatOperation;
import com.nascentdigital.pipeline.operations.DistinctOperation;
import com.nascentdigital.pipeline.operations.FilterOperation;
import com.nascentdigital.pipeline.operations.FlatProjectionOperation;
import com.nascentdigital.pipeline.operations.GroupByOperation;
import com.nascentdigital.pipeline.operations.IterableSourceOperation;
import com.nascentdigital.pipeline.operations.ProjectionOperation;
import com.nascentdigital.pipeline.operations.SkipOperation;
import com.nascentdigital.pipeline.operations.TakeOperation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides a flexible and fast way of manipulating streams of data.
 *
 * @param <TElement> The type of elements that stream from the pipeline.
 */
public final class Pipeline<TElement> implements Iterable<TElement> {


    // region instance variables

    private final PipelineOperation<TElement> _operation;

    // endregion


    // region constructors

    private Pipeline(PipelineOperation<TElement> operation) {
        _operation = operation;
    }

    // endregion


    // region creation

    /**
     * Creates a new {@link Pipeline} using the specified array as the initial sequence source.
     *
     * @param source     An array to be used as a source.
     * @param <TElement> The type of the elements in the array.
     */
    @SuppressWarnings("unchecked")
    public static <TElement> Pipeline<TElement> from(TElement[] source) {
        return new Pipeline<>(new ArraySourceOperation<>(source));
    }

    /**
     * Creates a new {@link Pipeline} using the specified {@link Iterable} as a provider of the
     * initial sequence source.
     *
     * @param source     An iterable instance whose iterators will be used as a sequence source.
     * @param <TElement> The type of elements emitted from the iterator.
     */
    @SuppressWarnings("unchecked")
    public static <TElement> Pipeline<TElement> from(Iterable<TElement> source) {
        return new Pipeline<>(new IterableSourceOperation<>(source));
    }

    // endregion


    // region concatenation

    /**
     * Concatenates another sequence into the pipeline.
     *
     * @param addition The sequence to concatenate to the first sequence.
     */
    public Pipeline<TElement> concat(Iterable<TElement> addition) {

        // return self if iterable is null
        if (addition == null) {
            return this;
        }

        // or return new pipeline using iterable
        return new Pipeline<>(new ConcatOperation<>(this, addition));
    }

    /**
     * Concatenates another sequence into the pipeline.
     *
     * @param addition The sequence to concatenate to the first sequence.
     */
    public Pipeline<TElement> concat(TElement[] addition) {

        // return self if array is null
        if (addition == null) {
            return this;
        }

        // TODO: create custom array concatenator for performance
        // or return new pipeline using array
        return new Pipeline<>(new ConcatOperation<>(this, Arrays.asList(addition)));
    }

    /**
     * Joins the values into a string using the specified separator and the default
     * `toString()` implementation for string elements.
     *
     * @param separator A separator to join the string representations of elements
     *                  in the sequence by
     */
    public String join(String separator) {

        String result = "";

        // iteratively join elements to result string
        Iterator<TElement> iterator = this.iterator();
        while (iterator.hasNext()) {
            result += (separator + iterator.next().toString());
        }

        // return
        return result;
    }

    // endregion


    // region filtering

    /**
     * Filters a sequence of values based on a predicate.
     *
     * @param predicate A function to test each element for a condition.
     */

    @Group(type = GroupType.Filtering)
    public Pipeline<TElement> where(Predicate<TElement> predicate) {
        return new Pipeline<>(new FilterOperation<>(this, predicate));
    }

    /**
     * Returns distinct elements from a sequence by using the default equality comparer to compare
     * values.
     */
    public Pipeline<TElement> distinct() {
        return new Pipeline<>(new DistinctOperation<>(this));
    }

    // endregion


    // region projection

    /**
     * Projects each element of a sequence into a new form.
     *
     * @param selector     A selector function to apply to each element.
     * @param <TProjected> The new type of the projected elements.
     */
    public <TProjected> Pipeline<TProjected> map(Selector<TElement, TProjected> selector) {
        return new Pipeline<>(new ProjectionOperation<>(this, selector));
    }

    /**
     * Projects each element of a sequence to an {@link Iterable} sub-sequence and flattens the
     * resulting sequences into one sequence.
     *
     * @param selector     A transform function to extract the sub-sequences that will be flattened.
     * @param <TProjected> The type of the elements of the sequence returned by <i>selector</i>.
     */
    public <TProjected> Pipeline<TProjected> flatMap(Selector<TElement,
            Iterable<TProjected>> selector) {
        return new Pipeline<>(new FlatProjectionOperation<>(this, selector));
    }

    // endregion


    // region partition operators

    /**
     * Bypasses a specified number of elements in a sequence and then returns the remaining
     * elements.
     * <p>
     * <p>
     * If count is less than or equal to zero, all elements of source are yielded.
     *
     * @param count The number of elements to skip before returning the remaining elements.
     */
    public Pipeline<TElement> skip(int count) {
        return new Pipeline<>(new SkipOperation<>(this, count));
    }


    /**
     * Skips all elements in an array for which the predicate is false and
     * returns all elements in a sequence, for which the predicate is FALSE
     *
     * @param predicate A function to test each element for a condition.
     */
    public Pipeline<TElement> skipWhile(Predicate<TElement> predicate) {

        //creating iterable data structure to hold valid elements
        ArrayList<TElement> resultArray = new ArrayList<TElement>();

        for (TElement element : this) {
            if (!predicate.predicate(element)) {
                resultArray.add(element);
            }

        }
        return Pipeline.from(resultArray);
    }


    /**
     * Returns a specified number of contiguous elements from the start of a sequence.
     * <p>
     * If count is less than or equal to zero, source is not enumerated and an empty
     * {@link Pipeline} is returned.
     *
     * @param count The maximum number of elements to return.
     */
    public Pipeline<TElement> take(int count) {
        return new Pipeline<>(new TakeOperation<>(this, count));
    }


    /**
     * Returns all elements in a sequence, for which the predicate is TRUE
     *
     * @param predicate A function to test each element for a condition.
     */
    public Pipeline<TElement> takeWhile(Predicate<TElement> predicate) {

        ArrayList<TElement> resultArray = new ArrayList<TElement>();

        for (TElement element : this) {
            if (predicate.predicate(element)) {
                resultArray.add(element);
            }

        }
        return Pipeline.from(resultArray);

    }

    // endregion


    // region aggregation

    /**
     * Returns the number of elements in a sequence.
     */
    public int count() {

        // start count at zero
        int count = 0;

        // count all elements
        Iterator<TElement> iterator = this.iterator();
        while (iterator.hasNext()) {
            ++count;
            iterator.next();
        }

        // return count
        return count;
    }

    // endregion


    // region grouping

    /**
     * Groups the elements of a sequence according to a specified key selector function.
     *
     * @param selector A function to extract the key for each element.
     * @param <TKey>   The type of the key returned by the function represented in <i>keySelector</i>.
     */
    public <TKey> Pipeline<Grouping<TKey, TElement>> groupBy(Selector<TElement, TKey> selector) {
        return new Pipeline<>(new GroupByOperation<>(this, selector));
    }

    // endregion


    // region quantification

    /**
     * Determines whether a sequence contains an element.
     *
     * @param tElement A TElement Object to search for in the sequence.
     */
    public boolean contains(TElement tElement) {

        Iterator<TElement> iterator = this.iterator();

        // if not empty source
        if (iterator.hasNext()) {

            // iterate through all elements, return true as soon as tElement is found
            for (TElement element : this) {

                // account for null-containing sequences
                if (element == null) {
                    if (tElement == null)
                        return true;
                    continue;
                }

                if (element.equals(tElement)) {
                    return true;
                }
            }
        }

        // fail after we process entire sequence without finding tElement
        return false;
    }

    /**
     * Determines whether all the elements of a sequence satisfy a condition.
     *
     * @param predicate A function to test each element for a condition.
     */
    public boolean all(Predicate<TElement> predicate) {

        // iterate through all elements, fail as soon as predicate test fails
        for (TElement element : this) {
            if (!predicate.predicate(element)) {
                return false;
            }
        }

        // succeed if we process without failing
        return true;
    }

    /**
     * Determines whether a sequence contains any elements.
     */
    public boolean any() {

        // simply test if there is a single item in the iterator
        return this.iterator().hasNext();
    }

    /**
     * Determines whether any element of a sequence satisfies a condition.
     *
     * @param predicate A function to test each element for a condition.
     */
    public boolean any(Predicate<TElement> predicate) {

        // iterate through all elements, succeed as soon as predicate test passes
        for (TElement element : this) {
            if (predicate.predicate(element)) {
                return true;
            }
        }

        // fail if we process without matching
        return false;
    }

    // endregion


    // region element operators

    /**
     * Returns the first element of a sequence.
     *
     * @throws NoElementFoundException The pipeline sequence is empty.
     */
    public TElement first() throws NoElementFoundException {

        // create iterator
        Iterator<TElement> iterator = this.iterator();

        // return first element if one is available
        if (iterator.hasNext()) {
            return iterator.next();
        }

        // or throw if nothing was found
        throw new NoElementFoundException("Pipeline is empty.");
    }

    /**
     * Returns the first element of a sequence that satisfies a specified condition, or throws a
     * {@link NoElementFoundException} if no matching element is found.
     *
     * @param predicate A function to test each element for a condition.
     * @return The first element in the sequence that passes the test in <i>predicate</i>.
     * @throws NoElementFoundException No element satisfies the condition in <i>predicate</i>,
     *                                 or the pipeline sequence is empty.
     */
    public TElement first(Predicate<TElement> predicate) throws NoElementFoundException {

        // iterate through pipeline sequence to find first match
        for (TElement element : this) {

            // return first element passing predicate test
            if (predicate.predicate(element)) {
                return element;
            }
        }

        // or throw if there is not match
        throw new NoElementFoundException("No element matching predicate.");
    }

    /**
     * Returns the first element of a sequence, or a default value if the sequence contains no
     * elements.
     */
    public TElement firstOrDefault() {

        // try to get first element
        try {
            return first();
        }

        // return default if not element was found
        catch (NoElementFoundException e) {
            return null;
        }
    }

    /**
     * Returns the first element of a sequence that satisfies a specified condition or a default
     * value if no such element is found.
     *
     * @param predicate A function to test each element for a condition.
     */
    public TElement firstOrDefault(Predicate<TElement> predicate) {

        // try to get first element matching predicate
        try {
            return first(predicate);
        }

        // return default if not element was found
        catch (NoElementFoundException e) {
            return null;
        }
    }

    // endregion


    // region conversion operations

    /**
     * Casts the elements of a sequence to the specified type.
     * <p>
     * This method is helpful when working with a sequence of objects that are known to be of a
     * common type, but that are currently not specific enough for manipulation.
     * <p>
     * If an element cannot be cast to type <i>TDerived</i>, this method will throw an exception.
     *
     * @param targetClass The type to cast the elements of the <i>source</i> pipeline to.
     * @param <TDerived>  The concrete type of the elements.
     * @throws ClassCastException
     */
    public <TDerived> Pipeline<TDerived> cast(Class<TDerived> targetClass) throws ClassCastException {
        return new Pipeline<>(new CastOperation<>(this, targetClass));
    }

    /**
     * Converts the sequence into an array.
     */
    public TElement[] toArray(Class<TElement> elementType) {

        // extract sequence from pipeline
        List<TElement> sequence = toList();

        // convert sequence to array
        TElement[] array = (TElement[]) Array.newInstance(elementType, sequence.size());
        return array.length == 0
                ? array
                : sequence.toArray(array);
    }

    /**
     * Converts the sequence into an {@link List}.
     */
    public List<TElement> toList() {

        // extract sequence from pipeline
        List<TElement> sequence = new ArrayList<>();
        for (TElement element : _operation) {
            sequence.add(element);
        }

        // return sequence
        return sequence;
    }

    /**
     * Creates a {@link Map} from the pipeline according to a specified key selector function.
     *
     * @param keySelector A function to extract a key from each element.
     * @param <TKey>      The type of the key returned by <i>keySelector</i>.
     * @throws DuplicateKeyException Thrown when the <i>keySelector</i> produces duplicate keys for
     *                               two elements.
     */
    public <TKey> Map<TKey, TElement> toMap(Selector<TElement, TKey> keySelector)
            throws DuplicateKeyException {

        // create map
        Map<TKey, TElement> map = new HashMap<>();

        // build map from elements in pipeline
        for (TElement element : this) {

            // get key for element using selector
            TKey key = keySelector.select(element);

            // throw if key is already mapped
            if (map.containsKey(key)) {
                throw new DuplicateKeyException("Duplicate key encountered: " + key);
            }

            // or map element using key
            map.put(key, element);
        }

        // return map
        return map;
    }

    /**
     * Creates a {@link Map} from the pipeline according to a specified key selector and value
     * selector functions.
     *
     * @param keySelector   A function to extract a key from each element.
     * @param valueSelector A transform function to produce a result map value from each element.
     * @param <TKey>        The type of the key returned by <i>keySelector</i>.
     * @param <TValue>      The type of the value returned by <i>valueSelector</i>.
     * @throws DuplicateKeyException Thrown when the <i>keySelector</i> produces duplicate keys for
     *                               two elements.
     */
    public <TKey, TValue> Map<TKey, TValue> toMap(Selector<TElement, TKey> keySelector,
                                                  Selector<TElement, TValue> valueSelector)
            throws DuplicateKeyException {

        // create map
        Map<TKey, TValue> map = new HashMap<>();

        // build map from elements in pipeline
        for (TElement element : this) {

            // get key for element using selector
            TKey key = keySelector.select(element);

            // throw if key is already mapped
            if (map.containsKey(key)) {
                throw new DuplicateKeyException("Duplicate key encountered: " + key);
            }

            // resolve value
            TValue value = valueSelector.select(element);

            // or map element using key
            map.put(key, value);
        }

        // return map
        return map;
    }

    // endregion

    // region repetition

    /**
     * Creates a sequence by repeating the value
     *
     * @param element The element that gets repeated
     * @param count   The number of time the element is repeated
     */
    public Pipeline<TElement> repeat(TElement element, int count) {
        ArrayList<TElement> resultArray = new ArrayList<TElement>();
        int i = 0;
        while (i < count) {
            resultArray.add(element);
        }

        return Pipeline.from(resultArray);
    }

    //endregion

    // region Iterable<TElement> interface

    @Override
    public Iterator<TElement> iterator() {
        return _operation.iterator();
    }

    // endregion

    // region set operations

    /**
     * Creates a union of the current sequence and another sequence in the pipeline.
     *
     * @param addition The sequence to take a union of with  the first sequence.
     */
    public Pipeline<TElement> union(Iterable<TElement> addition) {

        // return self if iterable is null
        if (addition == null) {
            return this;
        } else {
            HashSet<TElement> resultSet = new HashSet<TElement>();
            Iterator<TElement> additional = addition.iterator();
            // add source to set
            for (TElement element : this) {
                resultSet.add(element);
            }
            // add additional sequence to set
            while (additional.hasNext()) {
                resultSet.add(additional.next());
            }

            return Pipeline.from(resultSet);
        }

    }

    /**
     * Creates an intersection of the current sequence and another sequence in the pipeline.
     *
     * @param addition The sequence to intersect of with the original sequence.
     */
    public Pipeline<TElement> intersect(Iterable<TElement> addition) {

        // return self if iterable is null
        if (addition == null) {
            return this;
        } else {
            HashSet<TElement> thisSet = new HashSet<TElement>();
            HashSet<TElement> additionSet = new HashSet<TElement>();
            Iterator<TElement> additional = addition.iterator();

            // add source to set
            for (TElement element : this) {
                thisSet.add(element);
            }
            // add additional sequence to set
            while (additional.hasNext()) {
                additionSet.add(additional.next());
            }
            thisSet.retainAll(additionSet);
            return Pipeline.from(thisSet);
        }

    }


    /**
     * Reverses a sequence.
     */
    public Pipeline<TElement> reverse() {
        ArrayList<TElement> resultArray = new ArrayList<>();
        Iterator<TElement> iterator = this.iterator();

        while (iterator.hasNext()) {
            resultArray.add(iterator.next());
        }

        Collections.reverse(resultArray);
        return Pipeline.from(resultArray);

    }

    // endregion


}

