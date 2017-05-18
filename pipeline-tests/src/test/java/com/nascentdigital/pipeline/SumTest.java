package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class SumTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldReturnZero_forBytes() {

        // create empty array
        final Byte[] source = new Byte[0];

        // use pipeline
        Byte value = Pipeline.from(source)
                .sumBytes(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.byteValue());
    }

    @Test
    public void emptySource_shouldReturnZero_forShorts() {

        // create empty array
        final Short[] source = new Short[0];

        // use pipeline
        Short value = Pipeline.from(source)
                .sumShorts(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.shortValue());
    }

    @Test
    public void emptySource_shouldReturnZero_forIntegers() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .sumInts(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.intValue());
    }

    @Test
    public void emptySource_shouldReturnZero_forLongs() {

        // create empty array
        final Long[] source = new Long[0];

        // use pipeline
        Long value = Pipeline.from(source)
                .sumLongs(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.longValue());
    }

    @Test
    public void emptySource_shouldReturnZero_forFloats() {

        // create empty array
        final Float[] source = new Float[0];

        // use pipeline
        Float value = Pipeline.from(source)
                .sumFloats(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.floatValue(), 0);
    }

    @Test
    public void emptySource_shouldReturnZero_forDoubles() {

        // create empty array
        final Double[] source = new Double[0];

        // use pipeline
        Double value = Pipeline.from(source)
                .sumDoubles(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(0, value.doubleValue(), 0);
    }

    // endregion


    // region singleton source

    @Test
    public void singleSource_shouldReturnSame_forBytes() {

        // create empty array
        final Byte[] source = new Byte[1];

        int count = 0;
        for (byte n = -1; n <= 2; ++n) {

            // update source
            source[0] = n;

            // use pipeline
            Byte value = Pipeline.from(source)
                    .sumBytes(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.byteValue());

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    @Test
    public void singleSource_shouldReturnSame_forShorts() {

        // create empty array
        final Short[] source = new Short[1];

        int count = 0;
        for (short n = -1; n <= 2; ++n) {

            // update source
            source[0] = n;

            // use pipeline
            Short value = Pipeline.from(source)
                    .sumShorts(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.shortValue());

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    @Test
    public void singleSource_shouldReturnSame_forIntegers() {

        // create empty array
        final Integer[] source = new Integer[1];

        int count = 0;
        for (int n = -1; n <= 2; ++n) {

            // update source
            source[0] = n;

            // use pipeline
            Integer value = Pipeline.from(source)
                    .sumInts(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.intValue());

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    @Test
    public void singleSource_shouldReturnSame_forLongs() {

        // create empty array
        final Long[] source = new Long[1];

        int count = 0;
        for (long n = -1; n <= 2; ++n) {

            // update source
            source[0] = n;

            // use pipeline
            Long value = Pipeline.from(source)
                    .sumLongs(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.longValue());

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    @Test
    public void singleSource_shouldReturnSame_forFloats() {

        // create empty array
        final Float[] source = new Float[1];

        int count = 0;
        for (float n = -1; n <= 2; n += 1) {

            // update source
            source[0] = n;

            // use pipeline
            Float value = Pipeline.from(source)
                    .sumFloats(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.floatValue(), 0);

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    @Test
    public void singleSource_shouldReturnZero_forDoubles() {

        // create empty array
        final Double[] source = new Double[1];

        int count = 0;
        for (double n = -1; n <= 2; n += 1) {

            // update source
            source[0] = n;

            // use pipeline
            Double value = Pipeline.from(source)
                    .sumDoubles(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value.doubleValue(), 0);

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnSum_forBytes() {

        // create array
        final Byte[] source = new Byte[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        byte sum = 0;
        for (Byte value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        byte value = Pipeline.from(source)
                .sumBytes(i -> i);

        // assert
        assertEquals(sum, value);
    }

    @Test
    public void manySource_shouldReturnSum_forShorts() {

        // create array
        final Short[] source = new Short[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        short sum = 0;
        for (Short value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        short value = Pipeline.from(source)
                .sumShorts(i -> i);

        // assert
        assertEquals(sum, value);
    }

    @Test
    public void manySource_shouldReturnSum_forIntegers() {

        // create array
        final Integer[] source = new Integer[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        int sum = 0;
        for (Integer value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        int value = Pipeline.from(source)
                .sumInts(i -> i);

        // assert
        assertEquals(sum, value);
    }

    @Test
    public void manySource_shouldReturnSum_forLongs() {

        // create array
        final Long[] source = new Long[] {
                -8l,
                9l,
                0l,
                null,
                12l
        };

        // calculate expected
        long sum = 0;
        for (Long value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        long value = Pipeline.from(source)
                .sumLongs(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(sum, value);
    }

    @Test
    public void manySource_shouldReturnSum_forFloats() {

        // create array
        final Float[] source = new Float[] {
                -8f,
                9f,
                0f,
                null,
                12f
        };

        // calculate expected
        float sum = 0;
        for (Float value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        float value = Pipeline.from(source)
                .sumFloats(i -> i);

        // assert
        assertEquals(sum, value, 0);
    }

    @Test
    public void manySource_shouldReturnSum_forDoubles() {

        // create array
        final Double[] source = new Double[] {
                -8.0,
                9.0,
                0.0,
                null,
                12.0
        };

        // calculate expected
        double sum = 0;
        for (Double value : source) {
            if (value != null) {
                sum += value;
            }
        }

        // use pipeline
        double value = Pipeline.from(source)
                .sumDoubles(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(sum, value, 0);
    }

    // endregion
}