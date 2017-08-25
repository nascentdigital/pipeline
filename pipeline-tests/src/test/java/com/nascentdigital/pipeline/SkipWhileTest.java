package com.nascentdigital.pipeline;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

/**
 * Created by kitty on 2017-05-25.
 */

public class SkipWhileTest extends PipelineTest {

    // region null source

    @Test
    public void firstSourceNull_shouldReturnAllIncludingNull() {

        // create array
        final Integer[] source = new Integer[] {
                null,
                8,
                9,
                10
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> false)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertNull(array[0]);
    }

    @Test
    public void lastSourceNull_shouldReturnAllIncludingNull(){

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                null
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> false)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertNull(array[array.length - 1]);
    }

    @Test
    public void sourceContainsMultipleNull_shouldReturnAll(){

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                null,
                null,
                10,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> false)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldReturnEmpty() {

        // create array
        Integer[] source = new Integer[] {
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne() {

        // create Array
        final Integer[] source = new Integer[] {
                9
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i < 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnEmpty() {

        // create Array
        final Integer[] source = new Integer[] {
                9
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region multiple source

    @Test
    public void manySource_shouldReturnAll() {

        // create Array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i < 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void manySource_shouldReturnEmpty() {

        // create Array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnAll_whenFirstSourceIsFalse() {

        // create Array
        final Integer[] source = new Integer[] {
                -1,
                8,
                9,
                10,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void manySource_shouldReturnLastSource_whenLastSourceIsFalse() {

        // create Array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                -1
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(source[source.length - 1], array[0]);
    }

    @Test
    public void manySource_shouldReturnSome() {

        // create Array
        final Integer[] source = new Integer[] {
                8,
                9,
                -1,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> i > 0)
                .toArray(Integer.class);

        Integer[] result = new Integer[] {
                -1,
                11
        };

        // assert
        assertNotNull(array);
        assertEquals(result.length, array.length);
        assertArrayEquals(result, array);
    }

    // endregion


    // region reuse

    @Test
    public void reuseSource_shouldReturnDifferent_whenChanged() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11
        };

        // create pipeline
        Pipeline<Integer> pipeline = Pipeline.from(source)
                .skipWhile(i -> i < 0);

        // use pipeline
        Integer[] array = pipeline.toArray(Integer.class);

        // assert
        assertNotNull(source);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);

        // modify source
        source[source.length - 1] = 12;

        array = pipeline.toArray(Integer.class);

        // assert
        assertNotNull(source);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
    }

    // endregion


    // region exception

    @Test
    public void manySource_shouldThrow_whenPredicateThrows() {

        // create array
        final Integer[] source = new Integer[] {
                2,
                0,
                3
        };

        // predict exception
        exception.expect(ArithmeticException.class);

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(i -> 12%i == 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
    }

    // endregion
}
