package com.nascentdigital.pipeline;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class DistinctTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldThrow_first() {

        // create empty array
        final Integer[] source = new Integer[0];

        // expect exception
        exception.expect(NoElementFoundException.class);

        // use pipeline
        Integer value = Pipeline.from(source)
                .first();

        // assert
        assertNotNull(value);
    }

    @Test
    public void emptySource_shouldThrow_firstWithPredicate() {

        // create empty array
        final Integer[] source = new Integer[0];

        // expect exception
        exception.expect(NoElementFoundException.class);

        // use pipeline
        Integer value = Pipeline.from(source)
                .first(n -> true);

        // assert
        assertNotNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_firstDefault() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .firstOrDefault();

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_firstDefaultWithPredicate() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .firstOrDefault(n -> true);

        // assert
        assertNull(value);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnOne_first() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer value = Pipeline.from(source)
                .first();

        // assert
        assertNotNull(value);
        assertEquals(8, value.intValue());
    }

    @Test
    public void singletonSource_shouldReturnOne_firstOrDefault() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer value = Pipeline.from(source)
                .firstOrDefault();

        // assert
        assertNotNull(value);
        assertEquals(8, value.intValue());
    }

    @Test
    public void singletonSource_shouldReturnOne_firstWithPredicateMatch() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // use pipeline
        Integer value = Pipeline.from(source)
                .first(n -> n < 10);

        // assert
        assertNotNull(value);
        assertEquals(8, value.intValue());
    }

    @Test
    public void singletonSource_shouldThrow_firstWithPredicateNoMatch() {

        // create singleton array
        final Integer[] source = new Integer[] {
                8
        };

        // expect exception
        exception.expect(NoElementFoundException.class);

        // use pipeline
        Integer value = Pipeline.from(source)
                .first(n -> n < 8);

        // assert
        assertNotNull(value);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnFirst_first() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer value = Pipeline.from(source)
                .skip(2)
                .first();

        // assert
        assertNotNull(value);
        assertEquals(10, value.intValue());
    }

    @Test
    public void manySource_shouldReturnFirst_firstOrDefault() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        Integer value = Pipeline.from(source)
                .skip(1)
                .firstOrDefault();

        // assert
        assertNotNull(value);
        assertEquals(9, value.intValue());
    }

    @Test
    public void manySource_shouldReturnFirst_firstWithPredicateMatch() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        final int skipped = 1;
        Integer value = Pipeline.from(source)
                .skip(skipped)
                .first(n -> n < 20);

        // assert
        assertNotNull(value);
        assertEquals(source[skipped], value);
    }

    @Test
    public void manySource_shouldReturnFirst_firstOrDefaultWithPredicateMatch() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // use pipeline
        final int skipped = 3;
        Integer value = Pipeline.from(source)
                .skip(skipped)
                .firstOrDefault(n -> n < 20);

        // assert
        assertNotNull(value);
        assertEquals(source[skipped], value);
    }

    @Test
    public void manySource_shouldThrow_firstWithPredicateNoMatch() {

        // create array
        final Integer[] source = new Integer[] {
                8,
                9,
                10,
                11,
                12
        };

        // expect exception
        exception.expect(NoElementFoundException.class);

        // use pipeline
        Integer value = Pipeline.from(source)
                .skip(3)
                .first(n -> n < 8);

        // assert
        assertNotNull(value);
    }

    // endregion
}
