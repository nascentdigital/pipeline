package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class TakeWhileTest extends PipelineTest {

    // region null source

   @Test
   public void firstSourceNull_shouldReturnAllIncludingNull(){

       // create array
       final Integer[] source = new Integer[] {
               null,
               9,
               10,
               11
       };

       // use pipeline
       Integer[] array = Pipeline.from(source)
               .takeWhile(i -> true)
               .toArray(Integer.class);

       // assert
       assertNotNull(array);
       assertEquals(source.length, array.length);
       assertArrayEquals(source, array);
       assertNotSame(source, array);
   }

   @Test
   public void lastSourceNull_shouldReturnAllIncludingNull(){

       // create array
       final Integer[] source = new Integer[] {
               9,
               10,
               11,
               null
       };

       // use pipeline
       Integer[] array = Pipeline.from(source)
               .takeWhile(i -> true)
               .toArray(Integer.class);

       // assert
       assertNotNull(array);
       assertEquals(source.length, array.length);
       assertArrayEquals(source, array);
       assertNotSame(source, array);
   }

   @Test
   public void sourceContainsSingleNull_shouldReturnAllIncludingNull(){

       // create array
       final Integer[] source = new Integer[] {
               9,
               10,
               null,
               11,
               12
       };

       // use pipeline
       Integer[] array = Pipeline.from(source)
               .takeWhile(i -> true)
               .toArray(Integer.class);

       // assert
       assertNotNull(array);
       assertEquals(source.length, array.length);
       assertArrayEquals(source, array);
       assertNotSame(source, array);
   }

   @Test
   public void sourceContainsMultipleNull_shouldReturnAllIncludingNull(){

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
               .takeWhile(i -> true)
               .toArray(Integer.class);

       // assert
       assertNotNull(source);
       assertEquals(source.length, array.length);
       assertArrayEquals(source, array);
       assertNotSame(source, array);
   }

    // endregion


    // region empty source

    @Test
    public void emptySouce_shouldReturnEmpty() {

        // create array
        final Integer[] source = new Integer[]{
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> true)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne() {

        // create array
        final Integer[] source = new Integer[] {
                9
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
    }

    @Test
    public void singletonSouce_shouldReturnEmpty() {

        // create array
        final Integer[] source = new Integer[] {
                9
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i >10)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

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
    public void manySource_shouldReturnEmpty_whenNoneMatch() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i < 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnAllbutLast_whenLastIsFalse() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                0
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i >0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length - 1, array.length);
        assertEquals(source[0], array[0]);
        assertEquals(source[1], array[1]);
    }

    @Test
    public void manySource_shouldReturnEmpty_whenFirstIsFalse() {

        // create array
        final Integer[] source = new Integer[] {
                0,
                8,
                9,
                10
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i > 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void manySource_shouldReturnSome_whenSomeMatch() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                3,
                12
        };

        // use pipeline
        Integer[] array = Pipeline.from(source)
                .takeWhile(i -> i < 10)
                .toArray(Integer.class);

        Integer[] result = new Integer[]{
                8,
                9
        };

        // assert
        assertNotNull(array);
        assertEquals(result.length, array.length);
        assertArrayEquals(array, result);
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
                .takeWhile(i -> i > 0);

        // use pipeline
        Integer[] array = pipeline.toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);

        // modify source
        source[source.length - 1] = 100;

        // reuse pipeline
        array = pipeline.toArray(Integer.class);

        //assert
        assertNotNull(array);
        assertEquals(source.length, array.length);
        assertArrayEquals(source, array);
        assertNotSame(source, array);
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
                .takeWhile(i -> 12%i == 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
    }

    // endregion
}