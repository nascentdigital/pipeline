package com.nascentdigital.pipeline;


import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class PipelineTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    public class BoxedValue<T> {
        T value;

        public BoxedValue() {
            value = null;
        }

        public BoxedValue(T value) {
            this.value = value;
        }
    }

    public class Pet {
        public final String name;
        public final int age;

        public Pet(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public class PetOwner {
        public final String name;
        public final Pet[] pets;

        public PetOwner(String name, Pet[] pets) {
            this.name = name;
            this.pets = pets;
        }
    }

    public class PetShop {
        public final String name;
        public final List<Pet> pets;

        public PetShop(String name, Pet[] pets) {
            this.name = name;
            this.pets = pets == null
                    ? null
                    : Arrays.asList(pets);
        }
    }

    protected static final String[] colors = {
            "red",
            "blue",
            "green",
            "yellow",
            "orange",
            "purple"
    };
    protected static final String[] shapes = {
            "circle",
            "oval",
            "rectangle",
            "square",
            "hexagon",
            "octagon"
    };
    protected static final Integer[] numbers = { 0, 1, 2, 3, 4, 5 };
}
