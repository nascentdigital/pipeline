package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class TakeWhileTest extends PipelineTest {

    // region null source

    @Test
    public void NullSource_shouldReturnEmptyArray() {

        // define source
        String[] source = null;

        // use pipeline with predicate
        String[] result = Pipeline.from(source)
                .takeWhile(
                        n -> {

                            return n.equals("");
                        }
                )
                .toArray(String.class);

        // assert
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    // endregion


    // region null-containing source

    @Test
    public void NullContainingSource_shouldThrow() {

        // define source
        String[] source = new String[]{null};

        // expect exception
        exception.expect(NullPointerException.class);

        // use pipeline with predicate
        String[] result = Pipeline.from(source)
                .takeWhile(
                        n -> {

                            return n.equals("");
                        }
                )
                .toArray(String.class);
    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldReturnEmpty() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(
                        n -> {
                            return true;
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
    public void singletonSource_shouldReturnOne_whenPredicateIsTrue() {

        // create singleton array
        final String[] source = new String[]{
                "test"
        };

        // use pipeline
        String[] array = Pipeline.from(source)
                .takeWhile(
                        n -> {

                            return n.equals("test");
                        }
                )
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertEquals("test", array[0]);
        assertNotSame(source, array);
    }

    @Test
    public void singletonSource_shouldReturnEmtpy_whenPredicateIsFalse() {

        // create singleton array
        final String[] source = new String[]{
                "test"
        };

        // use pipeline
        String[] result = Pipeline.from(source)
                .takeWhile(
                        n -> {

                            return n.equals("not test");
                        }
                )
                .toArray(String.class);

        // assert
        assertNotNull(result);
        assertEquals(0, result.length);
        assertArrayEquals(new String[]{}, result);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldTakeAll_whenPredicateIsTRUE() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] array = Pipeline.from(source).takeWhile(
                n -> {
                    return true;

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
    public void manySource_shouldTakeNone_whenPredicateFALSE() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] array = Pipeline.from(source).takeWhile(
                n -> {

                    return false;
                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
        assertArrayEquals(array, new Integer[]{});
    }

    @Test
    public void manySource_shouldReturnSome_forNonEdgeCasePredicate() {

        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        Integer[] array = Pipeline.from(source).takeWhile(
                n -> {

                    return n == 0;
                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertArrayEquals(array, new Integer[]{0});
    }

    @Test
    public void reusedSource_shouldReturnDifferent_whenChanged() {

        // create array
        final Integer[] source = new Integer[]{
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline (and cache it)
        Pipeline<Integer> pipeline = Pipeline.from(source);
        Integer[] array = pipeline.takeWhile(
                n -> {

                    return n <= 10;
                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(3, array.length);
        assertArrayEquals(Arrays.copyOfRange(source, 0, 3), array);
        assertArrayEquals(array, new Integer[]{8, 9, 10});

        // change source
        source[2] = 4;

        // use pipeline again
        array = pipeline.takeWhile(
                n -> {

                    return n <= 10;
                })
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(3, array.length);
        assertArrayEquals(Arrays.copyOfRange(source, 0, 3), array);
        assertArrayEquals(array, new Integer[]{8, 9, 4});
    }

    // endregion
}