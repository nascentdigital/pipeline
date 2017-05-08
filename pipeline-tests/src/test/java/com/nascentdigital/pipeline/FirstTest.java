package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class FirstTest extends PipelineTest {


    // region null source
    @Test
    public void first_nullSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
                null
        };

        // use pipeline
        Integer result = Pipeline.from(source).first();

        //assert
        assertNull(result);
    }

    @Test //#AskSim: Is method name too long?
    public void first_with_predicate_nullSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
                null
        };


        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).first(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on value of null
                    return n == null;
                });


        // assert
        assertEquals(1, counter.value.intValue()); // returns 1 since it finds "null" at index 0
        assertNull(result);
    }

    @Test
    public void firstOrDefault_nullSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
                null
        };

        // use pipeline
        Integer result = Pipeline.from(source).firstOrDefault();

        // assert
        assertNull(result);
    }

    @Test //#AskSim: Is method name too long?
    public void firstOrDefault_with_predicate_nullSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
                null
        };


        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).firstOrDefault(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on value of null
                    return n == null;
                });


        // assert
        assertEquals(1, counter.value.intValue()); // returns 1 since it finds "null" at index 0
        assertNull(result);
    }

    //endregion

    // region empty source

    @Test
    public void first_emptySource_shouldThrow() {

        // define source
        Integer[] source = new Integer[]{
        };

        // predict exception
        exception.expect(NoElementFoundException.class);

        // use pipeline
        Integer result = Pipeline.from(source).first();

        //should not reach this

        assertNull(result);
    }

    @Test //#AskSim: Is method name too long?
    public void first_with_predicate_emptySource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
        };

        // predict exception
        exception.expect(NoElementFoundException.class);

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).first(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on value of null
                    return n == null; // #AskSim: doesn't lambda evaluate n as null though? since sequence is empty
                });

        // should not reach
        assertNull(result);
    }

    @Test
    public void firstOrDefault_emptySource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
        };

        // use pipeline
        Integer result = Pipeline.from(source).firstOrDefault();

        //assert
        assertNull(result);
    }


    @Test //#AskSim: Is method name too long?
    public void firstOrDefault_with_predicate_emptySource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{
        };

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).firstOrDefault(
                n -> {

                    // code in this scope should not be reached as the lambda sees the collection to iterate through is empty
                    counter.value = counter.value + 1;
                    return n == null; // #AskSim: doesn't lambda evaluate n as null though? since sequence is empty
                });

        // assert that result is null
        assertEquals(result, null);
        assertEquals(0, counter.value.intValue()); // returns 1 since it finds "null" at index 0
    }


    //endregion

    // region singleton source

    @Test
    public void first_singleSource_shouldReturnFirstElement() {
        // define source
        Integer[] source = new Integer[]{Integer.MAX_VALUE
        };

        // use pipeline
        Integer result = Pipeline.from(source).first();

        // assert
        assertEquals(source.length, 1);
        assertEquals(result, Integer.valueOf(Integer.MAX_VALUE));
    }

    @Test //#AskSim: Is method name too long?
    public void first_with_predicate_singleSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{Integer.MAX_VALUE
        };

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).first(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on equality
                    return n == Integer.MAX_VALUE;
                });

        //assert
        assertEquals(1, counter.value.intValue());
        assertEquals(result, Integer.valueOf(Integer.MAX_VALUE));
    }


    @Test
    public void firstOrDefault_singleSource_shouldReturnFirstElement() {
        // define source
        Integer[] source = new Integer[]{Integer.MIN_VALUE
        };

        // use pipeline
        Integer result = Pipeline.from(source).firstOrDefault();

        // assert
        assertEquals(source.length, 1);
        assertEquals(result, Integer.valueOf(Integer.MIN_VALUE));
    }

    @Test //#AskSim: Is method name too long?
    public void firstOrDefault_with_predicate_singleSource_shouldReturnNull() {

        // define source
        Integer[] source = new Integer[]{Integer.MIN_VALUE
        };

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).firstOrDefault(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on equality
                    return n == Integer.MIN_VALUE;
                });

        //assert
        assertEquals(1, counter.value.intValue());
        assertEquals(result, Integer.valueOf(Integer.MIN_VALUE));
    }


    // endregion

    // region many source

    @Test
    public void first_manySource_shouldReturnFirstElement() {
        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3, 4 / 2,
        };

        // use pipeline
        Integer result = Pipeline.from(source).first();

        // assert
        assertEquals(source.length, 6);
        assertEquals(result, Integer.valueOf(0));
    }

    @Test //#AskSim: Is method name too long?
    public void first_with_predicate_manySource_shouldReturnNull() {
        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3, 4 / 2,
        };

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).first(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on equality
                    return n == Integer.MAX_VALUE;
                });

        //assert
        assertEquals(2, counter.value.intValue());
        assertEquals(result, Integer.valueOf(Integer.MAX_VALUE));
    }


    @Test
    public void firstOrDefault_manySource_shouldReturnFirstElement() {
        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3, 4 / 2,
        };

        // use pipeline
        Integer result = Pipeline.from(source).firstOrDefault();

        // assert
        assertEquals(source.length, 6);
        assertEquals(result, Integer.valueOf(0));
    }


    @Test //#AskSim: Is method name too long?
    public void firstOrDefault_with_predicate_manySource_shouldReturnNull() {
        // define source
        Integer[] source = new Integer[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 3
        };

        // use pipeline with predicate
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        Integer result = Pipeline.from(source).firstOrDefault(
                n -> {

                    // increment counter
                    counter.value = counter.value + 1;

                    //pass on equality
                    return n == Integer.MAX_VALUE;
                });

        //assert
        assertEquals(2, counter.value.intValue());
        assertEquals(result, Integer.valueOf(Integer.MAX_VALUE));
    }


    // endregion
}
