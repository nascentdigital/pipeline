package com.nascentdigital.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ContainsTest extends PipelineTest {

    // region null source
    @Test
    public void contains_nullSource_shouldReturnFalse() {

        // define source
        Integer[] source = null;

        // use pipeline
        Boolean result = Pipeline.from(source).contains(null);

        // assert
        assertNotNull(result);
        assertEquals(result, false);
    }

    //endregion

    // region null source
    @Test
    public void contains_nullContainingSource_shouldReturnFalse() {

        // define source
        Integer[] source = new Integer[]{
                null
        };

        // use pipeline
        Boolean result = Pipeline.from(source).contains(null);

        // assert
        assertEquals(source.length, 1);
        assertTrue(result);
    }

    //endregion

    // region empty source

    @Test
    public void contains_emptySource_shouldReturnFalse() {

        // define source
        String[] source = new String[]{
        };

        // use pipeline
        Boolean result = Pipeline.from(source).contains("");

        // assert
        assertEquals(source.length, 0);
        assertFalse(result);
    }


    //endregion

    // region singleton source

    @Test
    public void contains_singleSource_shouldReturnTrueForElementInSequence() {
        // define source
        Boolean[] source = new Boolean[]{Boolean.FALSE
        };

        // use pipeline
        Boolean result = Pipeline.from(source).contains(false);

        // assert
        assertEquals(source.length, 1);
        assertTrue(result);

    }

    // endregion

    // region many source

    @Test
    public void contains_manySource_shouldReturnFalseForElementNotInSequence() {
        // define source
        Boolean[] source = new Boolean[]{null, null, true, true, Boolean.TRUE
        };

        // use pipeline
        Boolean result = Pipeline.from(source).contains(Boolean.FALSE);

        // assert
        assertEquals(source.length, 5);
        assertFalse(result);

    }


    // endregion
}