package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class MinTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldReturnNull_forBytes() {

        // create empty array
        final Byte[] source = new Byte[0];

        // use pipeline
        Byte value = Pipeline.from(source)
                .minByte(i -> i);

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_forShorts() {

        // create empty array
        final Short[] source = new Short[0];

        // use pipeline
        Short value = Pipeline.from(source)
                .minShort(i -> i);

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_forIntegers() {

        // create empty array
        final Integer[] source = new Integer[0];

        // use pipeline
        Integer value = Pipeline.from(source)
                .minInteger(i -> i);

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_forLongs() {

        // create empty array
        final Long[] source = new Long[0];

        // use pipeline
        Long value = Pipeline.from(source)
                .minLong(i -> i);

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_forFloats() {

        // create empty array
        final Float[] source = new Float[0];

        // use pipeline
        Float value = Pipeline.from(source)
                .minFloat(i -> i);

        // assert
        assertNull(value);
    }

    @Test
    public void emptySource_shouldReturnNull_forDoubles() {

        // create empty array
        final Double[] source = new Double[0];

        // use pipeline
        Double value = Pipeline.from(source)
                .minDouble(i -> i);

        // assert
        assertNull(value);
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
                    .minByte(i -> i);

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
                    .minShort(i -> i);

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
                    .minInteger(i -> i);

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
                    .minLong(i -> i);

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
                    .minFloat(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value, 0);

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
                    .minDouble(i -> i);

            // assert
            assertNotNull(value);
            assertEquals(n, value, 0);

            // increment count
            ++count;
        }

        // ensure looped as expected
        assertEquals(count, 4);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldReturnSmallest_forBytes() {

        // create array
        final Byte[] source = new Byte[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        Byte min = Byte.MAX_VALUE;
        for (Byte value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Byte value = Pipeline.from(source)
                .minByte(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value);
    }

    @Test
    public void manySource_shouldReturnSmallest_forShorts() {

        // create array
        final Short[] source = new Short[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        Short min = Short.MAX_VALUE;
        for (Short value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Short value = Pipeline.from(source)
                .minShort(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value);
    }

    @Test
    public void manySource_shouldReturnSmallest_forIntegers() {

        // create array
        final Integer[] source = new Integer[] {
                -8,
                9,
                0,
                null,
                12
        };

        // calculate expected
        Integer min = Integer.MAX_VALUE;
        for (Integer value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Integer value = Pipeline.from(source)
                .minInteger(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value);
    }

    @Test
    public void manySource_shouldReturnSmallest_forLongs() {

        // create array
        final Long[] source = new Long[] {
                -8L,
                9L,
                0L,
                null,
                12L
        };

        // calculate expected
        Long min = Long.MAX_VALUE;
        for (Long value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Long value = Pipeline.from(source)
                .minLong(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value);
    }

    @Test
    public void manySource_shouldReturnSmallest_forFloats() {

        // create array
        final Float[] source = new Float[] {
                -8f,
                9f,
                0f,
                null,
                12f
        };

        // calculate expected
        Float min = Float.MAX_VALUE;
        for (Float value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Float value = Pipeline.from(source)
                .minFloat(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value, 0);
    }

    @Test
    public void manySource_shouldReturnSmallest_forDoubles() {

        // create array
        final Double[] source = new Double[] {
                -8.0,
                9.0,
                0.0,
                null,
                12.0
        };

        // calculate expected
        Double min = Double.MAX_VALUE;
        for (Double value : source) {
            if (value != null && value < min) {
                min = value;
            }
        }

        // use pipeline
        Double value = Pipeline.from(source)
                .minDouble(i -> i);

        // assert
        assertNotNull(value);
        assertEquals(min, value, 0);
    }

    // endregion
}