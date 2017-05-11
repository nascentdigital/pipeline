package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class SkipWhileTest extends PipelineTest {

    @Test
    public void test_input_next() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer result = Pipeline.from(source).skipWhile(
                n -> {

                    //pass on true evaluation of condition
                    return n <= new Integer(0);
                }).first();

        //assert
        assertEquals(result, Integer.valueOf(Integer.MAX_VALUE));

    }


/*
    // region empty source

    @Test
    public void emptySource_shouldReturnNone_whenNegative() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(-3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    @Test
    public void emptySource_shouldReturnNone_whenZero() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    @Test
    public void emptySource_shouldReturnNone_whenPositive() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne_whenNegative() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(-3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(8, array[0].intValue());
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnOne_whenZero() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(8, array[0].intValue());
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnNone_whenOne() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(1)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnNone_whenMany() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnAll_whenNegative() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(-3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void manySource_shouldReturnAll_whenZero() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void manySource_shouldReturnAllExceptFirst_whenOne() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(1)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length - 1, array.length);
        assertEquals(4, array.length);
        assertEquals(source[1], array[0]);
        assertEquals(source[2], array[1]);
        assertEquals(source[3], array[2]);
        assertEquals(source[4], array[3]);
    }

    @Test
    public void manySource_shouldReturnAllExceptMany_whenMany() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        final int count = 3;
        Integer[] array = Pipeline.from(source)
                .skip(count)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length - count, array.length);
        assertEquals(2, array.length);
        assertEquals(source[3], array[0]);
        assertEquals(source[4], array[1]);
    }

    @Test
    public void manySource_shouldReturnNone_whenEqualToSource() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(source.length)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnNone_whenMoreThanSource() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skip(source.length * 2)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }
*/

    // endregion
}