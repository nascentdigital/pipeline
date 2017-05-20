package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;


public class SkipWhileTest extends PipelineTest {

    // region null source

    @Test
    public void NullSource_shouldThrow() {

        // define source
        Integer[] source = new Integer[]{null};

        // expect exception
        exception.expect(NoElementFoundException.class);
        // use pipeline with predicate
        Pipeline result = Pipeline.from(source).skipWhile(
                n -> n == null
        );

        assertNull(result.first());

    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldReturnNone() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .skipWhile(
                        n -> {
                            return false;
                        }
                )
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertNotSame(array, source);
    }

    // endregion

    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne_whenPredicateIsFalse() {

        // create singleton array
        final String[] source = new String[]{
                "test"
        };

        // use pipeline
        String[] array = Pipeline.from(source)
                .skipWhile(
                        n ->
                        {

                            return !n.equals("test");
                        }

                )
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals("test", array[0]);
        assertNotSame(source, array);
    }

    // endregion

    // region many source

    @Test
    public void manySource_shouldSkipOverNone_whenPredicateIsFALSE() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] array = Pipeline.from(source).skipWhile(
                n -> {
                    return false;

                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(5, array.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(source[i], array[i]);
        }
        assertNotSame(source, array);
        assertArrayEquals(array, source);
        assertEquals(source[4].intValue(), array[4].intValue());

    }


    @Test
    public void manySource_shouldSkipOverAll_whenPredicateTRUE() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] array = Pipeline.from(source).skipWhile(
                n -> {

                    return true;
                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertArrayEquals(array, new Integer[]{});
    }

    //Todo: give method better name
    @Test
    public void manySource_shouldReturnSome_forNormalPredicate() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] result = Pipeline.from(source).skipWhile(
                n -> {

                    return n <= 0;
                }).toArray(Integer.class);

        // assert
        assertEquals(result[0], Integer.valueOf(Integer.MAX_VALUE));
        assertNotNull(result);
        assertEquals(2, result.length);
        assertArrayEquals(result, new Integer[]{Integer.MAX_VALUE, 3});

    }

    // endregion
}