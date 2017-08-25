```
// create array
Integer[] array = new Integer[] {
        8,
        9,
        0
};
// use pipeline
Integer result = Pipeline.from(array)
        .maxInteger(i -> i);
// result is 9
