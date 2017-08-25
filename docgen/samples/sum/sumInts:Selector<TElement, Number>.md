```
// create array
Integer[] array = new Integer[] {
        8,
        9,
        0
};
// use pipeline
Integer result = Pipeline.from(array)
        .sumInts(i -> i);
// result is 17
