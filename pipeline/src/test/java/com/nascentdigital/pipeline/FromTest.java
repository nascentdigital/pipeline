package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class FromTest extends PipelineTest {

    // region array

    @Test
    public void array_shouldCopySource_whenMany() {

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

    // endregion


    // region list

    @Test
    public void list_shouldCopySource_whenMany() {

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