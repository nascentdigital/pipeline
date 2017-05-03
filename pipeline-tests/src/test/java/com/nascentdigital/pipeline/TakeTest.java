package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class TakeTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldReturnNone_whenNegative() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .take(-3)
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
                .take(0)
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
                .take(3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(source, array);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnNone_whenNegative() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .take(-3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void singletonSource_shouldReturnNone_whenZero() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .take(0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void singletonSource_shouldReturnOne_whenOne() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .take(1)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(8, array[0].intValue());
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnOne_whenMany() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .take(3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(8, array[0].intValue());
        assertNotSame(source, array);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnNone_whenNegative() {

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
                .take(-3)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnNone_whenZero() {

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
                .take(0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnFirst_whenOne() {

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
                .take(1)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(source[0], array[0]);
    }

    @Test
    public void manySource_shouldReturnFirstMany_whenMany() {

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
                .take(count)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(count, array.length);
        assertEquals(source[0], array[0]);
        assertEquals(source[1], array[1]);
        assertEquals(source[2], array[2]);
    }

    @Test
    public void manySource_shouldReturnAll_whenEqualToSource() {

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
                .take(source.length)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void manySource_shouldReturnAll_whenMoreThanSource() {

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
                .take(source.length * 2)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    // endregion
}