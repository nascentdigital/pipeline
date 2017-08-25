```
// create array
Integer[] array = new Integer[] {
        2,
        4,
        5,
        6
};
// use pipeline
Integer result = Pipeline.from(array)
        .first();
// result is 2