package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;


public class UnionTest extends PipelineTest {

    // region null source

    @Test
    public void nullSource_shouldReturnEmpty() {

        // create collection
        List<String> colorsList = null;
        List<String> moreColorsList = null;

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region empty source


    @Test
    public void emptySource_shouldReturnEmpty() {

        // create collection
        final String[] colors = {};
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(colors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSources_shouldReturnTwoElements() {

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
                .union(moreColorsList)
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
    public void manySource_withUniqueElements_shouldReturnAll() {

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
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(colors.length + moreColors.length, array.length);

    }


    @Test
    public void manySource_withDuplicateElements_shouldReturnUnique() {

        // create collection
        final String[] colors = {
                "red",
                "red",
                "green"
        };

        final String[] moreColors = {
                "green",
                "blue",
                "yellow"
        };
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(moreColors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(4, array.length);
        assertTrue(Arrays.asList(array).contains("red"));
        assertTrue(Arrays.asList(array).contains("green"));
        assertTrue(Arrays.asList(array).contains("blue"));
        assertTrue(Arrays.asList(array).contains("yellow"));
        assertFalse(Arrays.asList(array).contains("orange"));
    }

    // endregion


    // region change source
    @Test
    public void reusedSource_shouldReturnDifferent_whenChanged() {

        // create collection
        final String[] colors = {
                "red",
                "red",
                "green"
        };
        final String[] moreColors = {
                "green",
                "blue",
                "yellow"
        };
        List<String> colorsList = Arrays.asList(colors);
        List<String> moreColorsList = Arrays.asList(moreColors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(4, array.length);
        assertTrue(Arrays.asList(array).contains("red"));
        assertTrue(Arrays.asList(array).contains("green"));
        assertTrue(Arrays.asList(array).contains("blue"));
        assertTrue(Arrays.asList(array).contains("yellow"));
        assertFalse(Arrays.asList(array).contains("orange"));

        // change source
        colorsList.set(2, "red");
        moreColorsList.set(2, "red");

        // use pipeline again
        array = Pipeline.from(colorsList)
                .union(moreColorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(3, array.length);
        assertTrue(Arrays.asList(array).contains("red"));
        assertTrue(Arrays.asList(array).contains("green"));
        assertTrue(Arrays.asList(array).contains("blue"));
        assertFalse(Arrays.asList(array).contains("yellow"));
    }

    // endregion
}