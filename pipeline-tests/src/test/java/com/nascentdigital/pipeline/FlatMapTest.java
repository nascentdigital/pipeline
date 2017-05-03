package com.nascentdigital.pipeline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class FlatMapTest extends PipelineTest {

    // region null source

    @Test
    public void nullSource_shouldIgnore_whenArray() {

        // copy original values
        final PetOwner[] owners = new PetOwner[] {
                new PetOwner("Sam", new Pet[] {
                        new Pet("George", 3),
                        new Pet("Jill", 7)
                }),
                new PetOwner("Sim", null),
                new PetOwner("Pam", new Pet[] {
                        new Pet("Foof", 10)
                })
        };

        // use pipeline
        Pet[] array = Pipeline.from(owners)
                .flatMap(o -> Pipeline.from(o.pets))
                .toArray(Pet.class);

        // assert
        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals("George", array[0].name);
        assertEquals("Jill", array[1].name);
        assertEquals("Foof", array[2].name);
    }

    @Test
    public void nullSource_shouldIgnore_whenList() {

        // copy original values
        final PetShop[] shops = new PetShop[] {
                new PetShop("PetSmart", new Pet[] {
                        new Pet("George", 3),
                        new Pet("Jill", 7)
                }),
                new PetShop("Pet Pound", null),
                new PetShop("Kitties R Us", new Pet[] {
                        new Pet("Foof", 10)
                })
        };

        // use pipeline
        Pet[] array = Pipeline.from(shops)
                .flatMap(s -> Pipeline.from(s.pets))
                .toArray(Pet.class);

        // assert
        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals("George", array[0].name);
        assertEquals("Jill", array[1].name);
        assertEquals("Foof", array[2].name);
    }

    // endregion
}