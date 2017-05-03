package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class ConcatTest extends PipelineTest {

    // region null source

    @Test
    public void nullSource_shouldCreateEmptySource_whenArray() {

        // copy original values
        final String[] colors = null;
        final String[] moreColors = null;

        // use pipeline
        String[] array = Pipeline.from(colors)
                .concat(moreColors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void nullSource_shouldCreateEmptySource_whenList() {

        // create collection
        List<String> colorsList = null;
        List<String> moreColorsList = null;

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .concat(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldCopySource_whenArray() {

        // copy original values
        final String[] colors = {};
        final String[] moreColors = {};

        // use pipeline
        String[] array = Pipeline.from(colors)
                .concat(moreColors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertNotSame(moreColors, array);
        assertEquals(0, array.length);
    }

    @Test
    public void emptySource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {};
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(colors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .concat(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldCopySource_whenArray() {

        // create arrays
        final String[] colors = {
                "red"
        };
        final String[] moreColors = {
                "blue"
        };

        // use pipeline
        String[] array = Pipeline.from(colors)
                .concat(moreColors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(2, array.length);
        assertEquals(colors[0], array[0]);
        assertEquals(moreColors[0], array[1]);
    }

    @Test
    public void singletonSource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {
                "red"
        };
        final String[] moreColors = {
                "blue"
        };
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(moreColors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .concat(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(2, array.length);
        assertEquals(colors[0], array[0]);
        assertEquals(moreColors[0], array[1]);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldCopySource_whenArray() {

        // copy original values
        final String[] colors = {
                "red",
                "blue",
                "green"
        };
        final String[] moreColors = {
                "yellow",
                "orange",
                "purple"
        };

        // use pipeline
        String[] array = Pipeline.from(colors)
                .concat(moreColors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(colors.length + moreColors.length, array.length);

        for (int i = 0; i < colors.length; ++i) {
            assertEquals(colors[i], array[i]);
        }

        for (int i = 0; i < moreColors.length; ++i) {
            assertEquals(moreColors[i], array[i + colors.length]);
        }
    }

    @Test
    public void manySource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {
                "red",
                "blue",
                "green"
        };
        final String[] moreColors = {
                "yellow",
                "orange",
                "purple"
        };
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(moreColors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .concat(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(colors.length + moreColors.length, array.length);

        for (int i = 0; i < colors.length; ++i) {
            assertEquals(colors[i], array[i]);
        }

        for (int i = 0; i < moreColors.length; ++i) {
            assertEquals(moreColors[i], array[i + colors.length]);
        }
    }

    // endregion
}