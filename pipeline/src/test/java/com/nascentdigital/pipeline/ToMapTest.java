package com.nascentdigital.pipeline;


import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;


public class ToMapTest extends PipelineTest {

    // region empty source

    @Test
    public void emptySource_shouldMapEmpty() {

        // create empty array
        final Pet[] source = new Pet[0];

        // use pipeline
        Map<Integer, Pet> map = Pipeline.from(source)
                .toMap(p -> p.age);

        // assert
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    public void emptySource_shouldMapEmpty_withValueSelector() {

        // create empty array
        final Pet[] source = new Pet[0];

        // use pipeline
        Map<Integer, String> map = Pipeline.from(source)
                .toMap(p -> p.age, p -> p.name);

        // assert
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldMapOne() {

        // create single element array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3)
        };

        // use pipeline
        Map<Integer, Pet> map = Pipeline.from(source)
                .toMap(p -> p.age);

        // assert
        assertNotNull(map);
        assertEquals(1, map.size());
        assertSame(source[0], map.get(3));
    }

    @Test
    public void singletonSource_shouldMapOne_withValueSelector() {

        // create single element array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3)
        };

        // use pipeline
        Map<Integer, String> map = Pipeline.from(source)
                .toMap(p -> p.age, p -> p.name);

        // assert
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(source[0].name, map.get(3));
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldMapAll_withUniqueKey() {

        // create array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3),
                new Pet("Tiger", 7),
                new Pet("Kitty", 5),
                new Pet("LJ", 5),
                new Pet("Garfield", 3),
                new Pet("Cheetaur", 6),
                new Pet("Liono", 5),
        };

        // use pipeline
        Map<String, Pet> map = Pipeline.from(source)
                .toMap(p -> p.name);

        // assert
        assertNotNull(map);
        assertEquals(source.length, map.size());
        assertSame(source[0], map.get("Whiskers"));

        // validate all elements
        for (int i = 0; i < source.length; ++i) {
            Pet pet = source[i];
            assertSame(pet, map.get(pet.name));
        }
    }

    @Test
    public void manySource_shouldThrow_whenDuplicateKey() {

        // create array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3),
                new Pet("Tiger", 7),
                new Pet("Kitty", 5),
                new Pet("LJ", 5),
                new Pet("Garfield", 3),
                new Pet("Cheetaur", 6),
                new Pet("Liono", 5),
        };

        exception.expect(DuplicateKeyException.class);

        // use pipeline
        Map<Integer, Pet> map = Pipeline.from(source)
                .toMap(p -> p.age);

        // assert
        assertNotNull(map);
    }

    @Test
    public void manySource_shouldMapAll_withValueSelector() {

        // create array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3),
                new Pet("Tiger", 7),
                new Pet("Kitty", 5),
                new Pet("LJ", 5),
                new Pet("Garfield", 3),
                new Pet("Cheetaur", 6),
                new Pet("Liono", 5),
        };

        // use pipeline
        Map<String, Integer> map = Pipeline.from(source)
                .toMap(p -> p.name, p -> p.age);

        // assert
        assertNotNull(map);
        assertEquals(source.length, map.size());
        assertSame(3, map.get("Whiskers").intValue());

        // validate all elements
        for (int i = 0; i < source.length; ++i) {
            Pet pet = source[i];
            assertSame(pet.age, map.get(pet.name).intValue());
        }
    }

    @Test
    public void manySource_shouldThrow_whenDuplicateKey_withValueSelector() {

        // create array
        final Pet[] source = new Pet[] {
                new Pet("Whiskers", 3),
                new Pet("Tiger", 7),
                new Pet("Kitty", 5),
                new Pet("LJ", 5),
                new Pet("Garfield", 3),
                new Pet("Cheetaur", 6),
                new Pet("Liono", 5),
        };

        exception.expect(DuplicateKeyException.class);

        // use pipeline
        Map<Integer, String> map = Pipeline.from(source)
                .toMap(p -> p.age, p -> p.name);

        // assert
        assertNotNull(map);
    }

    // endregion
}
