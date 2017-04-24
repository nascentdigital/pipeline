package com.nascentdigital.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;


public class ArrayTest extends PipelineTest {

    @Test
    public void concat_shouldAddArrayWithManyValues() {

        // copy original values
        String[] originalColors = Arrays.copyOf(colors, colors.length);
        assertNotSame(colors, originalColors);
        String[] originalShapes = Arrays.copyOf(shapes, shapes.length);
        assertNotSame(shapes, originalShapes);

        // use pipeline
        String[] array = Pipeline.from(colors)
                .concat(shapes)
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertArrayEquals(originalColors, colors);
        assertArrayEquals(originalShapes, shapes);
        assertEquals(colors.length + shapes.length, array.length);
        assertArrayEquals(colors, Arrays.copyOf(array, colors.length));
        assertArrayEquals(shapes, Arrays.copyOfRange(array, colors.length, array.length));
    }

    @Test
    public void map_shouldConvertValuesToDifferentTypes() {

        // use pipeline
        String[] array = Pipeline.from(numbers)
                .map(n -> colors[n])
                .toArray(String.class);

        // assert
        assertNotNull(array);
        assertNotSame(colors, array);
        assertArrayEquals(colors, array);
        assertEquals(colors.length, numbers.length);
    }

    @Test
    public void where_shouldFilterSomeValues() {

        // use pipeline
        Integer[] array = Pipeline.from(numbers)
                .where(n -> (n % 2) == 0)
                .toArray(Integer.class);

        // assert
        assertNotNull(array);
        assertNotSame(numbers, array);
        assertEquals(3, array.length);
    }

    @Test
    public void count_shouldMatchSource() {

        // use pipeline
        int count = Pipeline.from(numbers).count();

        // assert
        assertEquals(numbers.length, count);
    }

    @Test
    public void count_shouldWorkWithEmptyArray() {

        // use pipeline
        int count = Pipeline.from(new Integer[0]).count();

        // assert
        assertEquals(0, count);
    }

    @Test
    public void count_shouldWorkWithSingletonArray() {

        // use pipeline
        int count = Pipeline.from(new String[] {""}).count();

        // assert
        assertEquals(1, count);
    }

    @Test
    public void all_shouldSucceedOnEmptyArray() {

        // use pipeline
        boolean result = Pipeline.from(new String[] {})
                .all(s -> false);

        // assert
        assertTrue(result);
    }

    @Test
    public void all_shouldFailAsSoonAsPossible() {

        // create array
        Integer[] numbers = new Integer[] {
                1,
                3,
                2,
                4
        };

        // use pipeline
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        boolean result = Pipeline.from(numbers)
                .all(n -> {

                    // increment count
                    counter.value = counter.value + 1;

                    // fail if even
                    return (n % 2) == 1;
                });

        // assert
        assertFalse(result);
        assertEquals(3, counter.value.intValue());
    }

    @Test
    public void any_shouldFailOnEmptyArray() {

        String[] array = new String[0];

        // use pipeline without predicate
        boolean result = Pipeline.from(array).any();
        assertFalse(result);

        // use pipeline with predicate that always passes (should still fail)
        result = Pipeline.from(array).any(s -> true);
        assertFalse(result);
    }

    @Test
    public void any_shouldPassAsSoonAsPossible() {

        // create array
        Integer[] numbers = new Integer[] {
                1,
                3,
                2,
                4
        };

        // use pipeline without predicate (should pass)
        boolean result = Pipeline.from(numbers).any();
        assertTrue(result);

        // use with predicate, counting passes
        BoxedValue<Integer> counter = new BoxedValue<>(0);
        result = Pipeline.from(numbers)
                .any(n -> {

                    // increment count
                    counter.value = counter.value + 1;

                    // pass on even
                    return (n % 2) == 0;
                });
        // assert
        assertTrue(result);
        assertEquals(3, counter.value.intValue());
    }

    @Test
    public void groupBy_shouldGroupMultipleKeysAndValues() {

        // create array
        Pet[] pets = new Pet[] {
                new Pet("Pam", 8),
                new Pet("Hannah", 1),
                new Pet("Ed", 5),
                new Pet("Andrea", 6),
                new Pet("Dee", 5),
                new Pet("Tom", 3),
                new Pet("Sim", 10),
                new Pet("Kay", 8),
                new Pet("Shawn", 6),
                new Pet("Sam", 5)
        };

        // use pipeline to create groupings
        Pipeline<Grouping<Integer, Pet>> groupings = Pipeline.from(pets)
                .groupBy(p -> p.age);
        assertNotNull(groupings);

        // process groupings
        int groupCount = 0;
        for (Grouping<Integer, Pet> grouping : groupings) {

            // increment group count
            ++groupCount;

            // get age group
            int ageGroup = grouping.key;

            // determine expected group size
            int groupSize = 0;
            for (Pet pet : pets) {
                if (pet.age == ageGroup) {

                    // increment size
                    ++groupSize;

                    // ensure pet name matches
                    boolean found = false;
                    for (Pet groupedPet : grouping) {
                        if (pet.name.equals(groupedPet.name)) {
                            found = true;
                            break;
                        }
                    }

                    // assert found
                    assertTrue("Expected pet to be in group: " + ageGroup, found);
                }
            }

            // assert group size
            List<Pet> groupedPets = new ArrayList<>();
            for (Pet pet : grouping) {
                groupedPets.add(pet);
            }
            assertEquals(groupSize, groupedPets.size());
        }

        // assert
        assertEquals(6, groupCount);
    }
}