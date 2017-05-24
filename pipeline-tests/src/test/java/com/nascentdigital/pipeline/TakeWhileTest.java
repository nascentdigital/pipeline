package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class TakeWhileTest extends PipelineTest {

    // region null source

    // endregion


    // region empty source

    // endregion


    // region singleton source

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnAll_whenAllMatch() {

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
                .takeWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }


    @Test
    public void manySource_shouldReturnSome_whenSomeMatch() {

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
                .takeWhile(i -> i < 11)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
//        assertEquals(source.length, array.length);
//        assertArrayEquals(source, array);
//        assertNotSame(source, array);
    }

    // endregion
}