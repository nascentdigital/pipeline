```
// create array
Integer[] array = new Integer[] {
        2,
        4,
        6,
        8
};
// use pipeline
boolean result = Pipeline.from(array)
        .all(n -> n%2 == 0);
// result is true