```
// create array
Integer[] array = new Integer[] {
        2,
        4,
        5,
        6
};
// use pipeline
boolean result = Pipeline.from(array)
        .any(n -> n%2 != 0);
// result is true