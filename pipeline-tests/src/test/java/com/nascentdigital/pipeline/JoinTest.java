package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class JoinTest extends PipelineTest {

    // region null source

    @Test
    public void nullSequence_shouldReturnEmptyString() {

        // create source
        final String[] colors = null;

        // use pipeline
        String result = Pipeline.from(colors)
                .join(".");

        // assert
        assertNotNull(result);
        assertEquals(result.length(), 0);
        assertEquals("", result);
    }

    @Test
    public void nullContainingSequence_shouldThrow() {

        // create collection containing only null
        List<String> colorsList = new ArrayList<>();
        colorsList.add(null);

        // use pipeline
        String result = Pipeline.from(colorsList)
                .join(".");

        // assert
        assertEquals("", result);

    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldReturnEmptyString() {

        // create source
        final String[] colors = {};

        // use pipeline
        String result = Pipeline.from(colors)
                .join("-");

        // assert
        assertNotNull(result);
        assertEquals(0, result.length());
        assertEquals(result, "");
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldReturnSingleElementString() {

        // create arrays
        final String[] colors = {
                "red"
        };

        // use pipeline
        String result = Pipeline.from(colors)
                .join("-");

        // assert
        assertNotNull(result);
        assertEquals(result, "red");

    }

    // endregion


    // region many source


    @Test
    public void manySource_shouldReturnJoinedString() {

        // create collection
        List<String> colorsList = new ArrayList<>();
        colorsList.add("red");
        colorsList.add("");
        colorsList.add("red");
        colorsList.add("blue");
        colorsList.add(" ");
        colorsList.add("green");
        colorsList.add("");

        // use pipeline
        String result = Pipeline.from(colorsList)
                .join("-");


        // assert
        assertNotNull(result);
        assertEquals(result, "red--red-blue- -green-");
    }

    @Test
    public void manySourceContainingNull_shouldNotThrow() {

        // copy original values
        final String[] colors = {
                "red",
                "blue",
                null,
                "green"
        };

        // use pipeline
        String result = Pipeline.from(colors)
                .join("~");

        // assert
        assertEquals("red~blue~~green", result);

    }


    // endregion
}