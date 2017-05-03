package com.nascentdigital.pipeline;


import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;


public class CastTest extends PipelineTest {

    // region Cat class

    class Cat extends Pet {
        public Cat(String name, int age) {
            super(name, age);
        }
    }

    // endregion


    // region Dog class

    class Dog extends Pet {
        public Dog(String name, int age) {
            super(name, age);
        }
    }

    // endregion


    // region empty source

    @Test
    public void emptySource_shouldCastEmpty() {

        // create empty array
        final Pet[] source = new Pet[0];

        // use pipeline
        Dog[] array = Pipeline.from(source)
                .cast(Dog.class)
                .toArray(Dog.class);

        // assert
        assertNotNull(array);
        assertEquals(0, array.length);
    }

    // endregion


    // region singleton source

    @Test
    public void singletonSource_shouldCastOne() {

        // create single element array
        final Pet[] source = new Pet[] {
                new Cat("Whiskers", 3)
        };

        // use pipeline
        Cat[] array = Pipeline.from(source)
                .cast(Cat.class)
                .toArray(Cat.class);

        // assert
        assertNotNull(array);
        assertEquals(1, array.length);
        assertSame(source[0], array[0]);
    }

    @Test
    public void singletonSource_shouldThrow_withMismatchedType() {

        // create single element array
        final Pet[] source = new Pet[] {
                new Dog("Bowser", 3)
        };

        // predict exception
        exception.expect(ClassCastException.class);

        // use pipeline
        Cat[] array = Pipeline.from(source)
                .cast(Cat.class)
                .toArray(Cat.class);

        // assert
        assertNotNull(array);
    }

    // endregion


    // region many source

    @Test
    public void manySource_shouldMapAll_whenHomogeneous() {

        // create array
        final Pet[] source = new Pet[] {
                new Cat("Whiskers", 3),
                new Cat("Tiger", 7),
                new Cat("Kitty", 5),
                new Cat("LJ", 5),
                new Cat("Garfield", 3),
                new Cat("Cheetaur", 6),
                new Cat("Liono", 5)
        };

        // use pipeline
        Cat[] array = Pipeline.from(source)
                .cast(Cat.class)
                .toArray(Cat.class);

        // assert
        assertNotNull(array);
        assertEquals(source.length, array.length);

        // validate all elements
        for (int i = 0; i < source.length; ++i) {
            assertSame(source[i], array[i]);
        }
    }

    @Test
    public void manySource_shouldThrow_whenHeterogeneous() {

        // create array
        final Pet[] source = new Pet[] {
                new Cat("Whiskers", 3),
                new Cat("Tiger", 7),
                new Cat("Kitty", 5),
                new Dog("Bowser", 5),
                new Cat("Garfield", 3),
                new Cat("Cheetaur", 6),
                new Cat("Liono", 5)
        };

        // predict exception
        exception.expect(ClassCastException.class);

        // use pipeline
        Cat[] array = Pipeline.from(source)
                .cast(Cat.class)
                .toArray(Cat.class);

        // assert
        assertNotNull(array);
    }

    // endregion
}
