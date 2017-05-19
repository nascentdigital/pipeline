package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class ReduceTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldReturnInitial() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer initial = 3;
        Integer value = Pipeline.from(source)
                .reduce((total, i) -> 10, initial);

        // assert
        assertNotNull(value);
        assertEquals(initial, value);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnTotal_whenAdded() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // determine expected
        Integer initial = 3;
        Integer expected = initial + source[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .reduce((total, i) -> total + i, initial);

        // assert
        assertNotNull(value);
        assertEquals(expected, value);
    }

    @Test
    public void singletonSource_shouldReturnTotal_whenMultiplied() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // determine expected
        Integer initial = 3;
        Integer expected = initial * source[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .reduce((total, i) -> total * i, initial);

        // assert
        assertNotNull(value);
        assertEquals(expected, value);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnTotal_whenAdded() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                -9,
                10,
                null,
                12
        };

        // determine expected
        Integer initial = 3;
        Integer expected = initial;
        for (Integer value : source) {
            if (value != null) {
                expected += value;
            }
        }

        // use pipeline
        Integer value = Pipeline.from(source)
                .reduce((total, i) -> i == null ? total : total + i, initial);

        // assert
        assertNotNull(value);
        assertEquals(expected, value);
    }

    @Test
    public void manySource_shouldReturnTotal_whenMultiplied() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                -9,
                10,
                null,
                12
        };

        // determine expected
        Integer initial = 3;
        Integer expected = initial;
        for (Integer value : source) {
            if (value != null) {
                expected *= value;
            }
        }

        // use pipeline
        Integer value = Pipeline.from(source)
                .reduce((total, i) -> i == null ? total : total * i, initial);

        // assert
        assertNotNull(value);
        assertEquals(expected, value);
    }

    @Test
    public void manySource_shouldReturnJoin_whenConcatenatingStrings() {

        // create array
        final String[] source = new String[] {
                "foo",
                "bar",
                "bell"
        };

        // determine expected
        String initial = "";
        String expected = initial;
        for (String value : source) {
            expected += value;
        }

        // use pipeline
        String value = Pipeline.from(source)
                .reduce((total, s) -> total + s, initial);

        // assert
        assertNotNull(value);
        assertEquals(expected, value);
        assertTrue(value.length() > 1);
    }

    // endregion
}