package com.nascentdigital.pipeline;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class DistinctTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldBeEmpty() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .distinct()
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .distinct()
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnAll_whenAllUnique() {

        // create array
        final String[] source = new String[] {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        };

        // use pipeline
        String[] array = Pipeline.from(source)
                .distinct()
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
    }

    @Test
    public void manySource_shouldReturnSome_whenSomeDuplicates() {

        // create array
        final String[] shapes = new String[] {
                "point",
                "circle",
                "square",
                "triangle"
        };
        final String[] actions = new String[] {
                "shake",
                "circle",
                "jump",
                "point",
                "pull"
        };
        final String[] intersection = new String[] {
                "point",
                "circle",
                "square",
                "triangle",
                "shake",
                "jump",
                "pull"
        };

        // use pipeline
        String[] array = Pipeline.from(shapes)
                .concat(actions)
                .distinct()
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(7, array.length);
        assertArrayEquals(intersection, array);
    }

    @Test
    public void manySource_shouldReturnOne_whenAllDuplicates() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                8,
                8,
                8
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .distinct()
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals(8, array[0].intValue());
    }

    // endregion
}
