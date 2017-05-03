package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class FromTest extends PipelineTest {

    // region null source

    @Test
    public void nullSource_shouldCreateEmptySource_whenArray() {

        // copy original values
        final String[] colors = null;

        // use pipeline
        String[] array = Pipeline.from(colors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    @Test
    public void nullSource_shouldCreateEmptySource_whenList() {

        // create collection
        List<String> colorsList = null;

        // use pipeline
        String[] array = Pipeline.from(colorsList)
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

        // use pipeline
        String[] array = Pipeline.from(colors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertEquals(0, array.length);
    }

    @Test
    public void emptySource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {};
        List<String> colorsList = Arrays.asList(colors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
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

        // copy original values
        final String[] colors = {
                "red"
        };
        final String[] originalColors = Arrays.copyOf(colors, colors.length);

        // use pipeline
        String[] array = Pipeline.from(colors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertEquals(1, array.length);
        assertArrayEquals(originalColors, colors);
        assertArrayEquals(colors, array);
    }

    @Test
    public void singletonSource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {
                "red"
        };
        List<String> colorsList = Arrays.asList(colors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertEquals(1, array.length);
        assertArrayEquals(colors, array);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldCopySource_whenArray() {

        // copy original values
        final String[] colors = {
                "red",
                "blue",
                "green",
                "yellow",
                "orange",
                "purple"
        };
        final String[] originalColors = Arrays.copyOf(colors, colors.length);

        // use pipeline
        String[] array = Pipeline.from(colors)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertArrayEquals(originalColors, colors);
        assertArrayEquals(colors, array);
    }

    @Test
    public void manySource_shouldCopySource_whenList() {

        // create collection
        final String[] colors = {
                "red",
                "blue",
                "green",
                "yellow",
                "orange",
                "purple"
        };
        List<String> colorsList = Arrays.asList(colors);

        // use pipeline
        String[] array = Pipeline.from(colorsList)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertArrayEquals(colors, array);
    }

    // endregion
}