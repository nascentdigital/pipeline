package com.nascentdigital.pipeline;


import org.junit.Rule;
import org.junit.rules.ExpectedException;

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
