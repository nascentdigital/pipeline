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
import java.util.HashMap;
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
     * @param source An array to be used as a source.
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
     * @param source An iterable instance whose iterators will be used as a sequence source.
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
     * @param selector A selector function to apply to each element.
     * @param <TProjected> The new type of the projected elements.
     */
    public <TProjected> Pipeline<TProjected> map(Selector<TElement, TProjected> selector) {
        return new Pipeline<>(new ProjectionOperation<>(this, selector));
    }

    /**
     * Projects each element of a sequence to an {@link Iterable} sub-sequence and flattens the
     * resulting sequences into one sequence.
     *
     * @param selector A transform function to extract the sub-sequences that will be flattened.
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
     *
     * If count is less than or equal to zero, all elements of source are yielded.
     *
     * @param count The number of elements to skip before returning the remaining elements.
     */
    public Pipeline<TElement> skip(int count) {
        return new Pipeline<>(new SkipOperation<>(this, count));
    }

    /**
     * Returns a specified number of contiguous elements from the start of a sequence.
     *
     * If count is less than or equal to zero, source is not enumerated and an empty
     * {@link Pipeline} is returned.
     *
     * @param count The maximum number of elements to return.
     */
    public Pipeline<TElement> take(int count) {
        return new Pipeline<>(new TakeOperation<>(this, count));
    }

    // endregion


    // region aggregation


    // region reduce

    /**
     * Combines the sequence into a singular {@link TOutput} value.
     *
     * @param aggregator A function that combines individual sequence elements into the previous
     *                   aggregator value.
     * @param initial    The initial value passed into the aggregator, which is the final result if
     *                   there are no elements in the sequence.
     * @param <TOutput>  The type of the value passed in/out of the aggregator, also representing
     *                   the final result of the <c>reduce()</c> method.
     */
    public <TOutput> TOutput reduce(Aggregator<TElement, TOutput> aggregator, TOutput initial) {

        // start aggregate as null
        TOutput aggregate = initial;

        // process all elements
        for (TElement element : this) {
            aggregate = aggregator.aggregate(aggregate, element);
        }

        // return aggregate
        return aggregate;
    }

    // endregion

    // region count

    /**
     * Returns the number of elements in a sequence, including <c>null</c> values.
     */
    public int count() {

        // count sequence items, including null
        int count = 0;
        for (TElement element : this) {
            ++count;
        }

        // return count
        return count;
    }

    /**
     * Returns the number of elements in a sequence that match the specified predicate.
     *
     * @param predicate  A predicate deciding what gets matched.
     */
    public int count(Predicate<TElement> predicate) {

        // evaluate all items matching a predicate
        int count = 0;
        for (TElement element : this) {
            if (predicate.evaluate(element)) {
                ++count;
            }
        }

        // return count
        return count;
    }

    // endregion

    // region sum

    /**
     * Computes the sum of a sequence of {@link Byte} values.
     *
     * @param selector A selector that targets the bytes being evaluated.
     */
    public byte sumBytes(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        byte total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.byteValue();
            }
        }

        // return sum
        return total;
    }

    /**
     * Computes the sum of a sequence of {@link Short} values.
     *
     * @param selector A selector that targets the shorts being evaluated.
     */
    public short sumShorts(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        short total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.shortValue();
            }
        }

        // return sum
        return total;
    }

    /**
     * Computes the sum of a sequence of {@link Integer} values.
     *
     * @param selector A selector that targets the integers being evaluated.
     */
    public int sumInts(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        int total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.intValue();
            }
        }

        // return sum
        return total;
    }

    /**
     * Computes the sum of a sequence of {@link Long} values.
     *
     * @param selector A selector that targets the longs being evaluated.
     */
    public long sumLongs(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        long total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.longValue();
            }
        }

        // return sum
        return total;
    }

    /**
     * Computes the sum of a sequence of {@link Float} values.
     *
     * @param selector A selector that targets the floats being evaluated.
     */
    public float sumFloats(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        float total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.floatValue();
            }
        }

        // return sum
        return total;
    }

    /**
     * Computes the sum of a sequence of {@link Integer} values.
     *
     * @param selector A selector that targets the doubles being evaluated.
     */
    public double sumDoubles(Selector<TElement, Number> selector) {

        // iterate through all values, adding as we go
        double total = 0;
        for (TElement element : this) {

            // get next value
            Number value = selector.select(element);

            // add value if it isn't null
            if (value != null) {
                total += value.doubleValue();
            }
        }

        // return sum
        return total;
    }

    // endregion

    // region min

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Byte minByte(Selector<TElement, Byte> selector) {

        // iterate through all values, looking for the smallest value
        Byte minimum = null;
        for (TElement element : this) {

            // get next value
            Byte value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) > 0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Short minShort(Selector<TElement, Short> selector) {

        // iterate through all values, looking for the smallest value
        Short minimum = null;
        for (TElement element : this) {

            // get next value
            Short value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) > 0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Integer minInteger(Selector<TElement, Integer> selector) {

        // iterate through all values, looking for the smallest value
        Integer minimum = null;
        for (TElement element : this) {

            // get next value
            Integer value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) > 0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Long minLong(Selector<TElement, Long> selector) {

        // iterate through all values, looking for the smallest value
        Long minimum = null;
        for (TElement element : this) {

            // get next value
            Long value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) > 0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Float minFloat(Selector<TElement, Float> selector) {

        // iterate through all values, looking for the smallest value
        Float minimum = null;
        for (TElement element : this) {

            // get next value
            Float value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) > 0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    /**
     * Returns the minimum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Double minDouble(Selector<TElement, Double> selector) {

        // iterate through all values, looking for the smallest value
        Double minimum = null;
        for (TElement element : this) {

            // get next value
            Double value = selector.select(element);

            // use value if null
            if (minimum == null) {
                minimum = value;
            }

            // or update minimum (if applicable)
            else if (value != null
                    && minimum.compareTo(value) >  0) {
                minimum = value;
            }
        }

        // return smallest value, or null if there are no matches
        return minimum;
    }

    // endregion

    // region max

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Byte maxByte(Selector<TElement, Byte> selector) {

        // iterate through all values, looking for the largest value
        Byte maximum = null;
        for (TElement element : this) {

            // get next value
            Byte value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Short maxShort(Selector<TElement, Short> selector) {

        // iterate through all values, looking for the largest value
        Short maximum = null;
        for (TElement element : this) {

            // get next value
            Short value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Integer maxInteger(Selector<TElement, Integer> selector) {

        // iterate through all values, looking for the largest value
        Integer maximum = null;
        for (TElement element : this) {

            // get next value
            Integer value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Long maxLong(Selector<TElement, Long> selector) {

        // iterate through all values, looking for the largest value
        Long maximum = null;
        for (TElement element : this) {

            // get next value
            Long value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Float maxFloat(Selector<TElement, Float> selector) {

        // iterate through all values, looking for the largest value
        Float maximum = null;
        for (TElement element : this) {

            // get next value
            Float value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    /**
     * Returns the maximum value in a sequence of values.
     *
     * @param selector Returns a numeric value for each element.
     */
    public Double maxDouble(Selector<TElement, Double> selector) {

        // iterate through all values, looking for the largest value
        Double maximum = null;
        for (TElement element : this) {

            // get next value
            Double value = selector.select(element);

            // use value if null
            if (maximum == null) {
                maximum = value;
            }

            // or update maximum (if applicable)
            else if (value != null
                    && maximum.compareTo(value) < 0) {
                maximum = value;
            }
        }

        // return largest value, or null if there are no matches
        return maximum;
    }

    // endregion


    // endregion


    // region grouping

    /**
     * Groups the elements of a sequence according to a specified key selector function.
     *
     * @param selector A function to extract the key for each element.
     * @param <TKey> The type of the key returned by the function represented in <i>keySelector</i>.
     */
    public <TKey> Pipeline<Grouping<TKey, TElement>> groupBy(Selector<TElement, TKey> selector) {
        return new Pipeline<>(new GroupByOperation<>(this, selector));
    }

    // endregion


    // region quantification

    /**
     * Determines whether all the elements of a sequence satisfy a condition.
     *
     * @param predicate A function to test each element for a condition.
     */
    public boolean all(Predicate<TElement> predicate) {

        // iterate through all elements, fail as soon as predicate test fails
        for (TElement element : this) {
            if (!predicate.evaluate(element)) {
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
            if (predicate.evaluate(element)) {
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

        // get first element, throwing if there isn't one
        TElement element = firstOrDefault();
        if (element == null) {
            throw new NoElementFoundException("Pipeline is empty.");
        }

        // return element
        return element;
    }

    /**
     * Returns the first element of a sequence that satisfies a specified condition, or throws a
     * {@link NoElementFoundException} if no matching element is found.
     *
     * @param predicate A function to test each element for a condition.
     * @return The first element in the sequence that passes the test in <i>predicate</i>.
     *
     * @throws NoElementFoundException  No element satisfies the condition in <i>predicate</i>,
     *                                  or the pipeline sequence is empty.
     */
    public TElement first(Predicate<TElement> predicate) throws NoElementFoundException {

        // get first element, throwing if there isn't one
        TElement element = firstOrDefault(predicate);
        if (element == null) {
            throw new NoElementFoundException("Specified predicate failed to match any element.");
        }

        // return element
        return element;
    }

    /**
     * Returns the first element of a sequence, or a default value if the sequence contains no
     * elements.
     */
    public TElement firstOrDefault() {

        // create iterator
        Iterator<TElement> iterator = this.iterator();

        // return first element if one is available
        if (iterator.hasNext()) {
            return iterator.next();
        }

        // or return default if there is no element
        return null;
    }

    /**
     * Returns the first element of a sequence that satisfies a specified condition or a default
     * value if no such element is found.
     *
     * @param predicate A function to test each element for a condition.
     */
    public TElement firstOrDefault(Predicate<TElement> predicate) {

        // iterate through pipeline sequence to find first match
        for (TElement element : this) {

            // return first element passing predicate test
            if (predicate.evaluate(element)) {
                return element;
            }
        }

        // or return default if there is no match
        return null;
    }

    // endregion


    // region conversion operations

    /**
     * Casts the elements of a sequence to the specified type.
     *
     * This method is helpful when working with a sequence of objects that are known to be of a
     * common type, but that are currently not specific enough for manipulation.
     *
     * If an element cannot be cast to type <i>TDerived</i>, this method will throw an exception.
     *
     * @param targetClass The type to cast the elements of the <i>source</i> pipeline to.
     * @param <TDerived> The concrete type of the elements.
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
     * @param <TKey> The type of the key returned by <i>keySelector</i>.
     * @throws DuplicateKeyException Thrown when the <i>keySelector</i> produces duplicate keys for
     *      two elements.
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
     * @param keySelector A function to extract a key from each element.
     * @param valueSelector A transform function to produce a result map value from each element.
     * @param <TKey> The type of the key returned by <i>keySelector</i>.
     * @param <TValue> The type of the value returned by <i>valueSelector</i>.
     * @throws DuplicateKeyException Thrown when the <i>keySelector</i> produces duplicate keys for
     *      two elements.
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


    // region Iterable<TElement> interface

    @Override
    public Iterator<TElement> iterator() {
        return _operation.iterator();
    }

    // endregion
}
