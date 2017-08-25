```
// create array
Integer[] array = new Integer[] {
        2,
        4,
        5,
        6
};
// use pipeline
Number[] result = Pipeline.from(array)
        .cast(Number.class)
        .toArray(Number.class);
// result is an array of Numbers with value{2,4,5,6}