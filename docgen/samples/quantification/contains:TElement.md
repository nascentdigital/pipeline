```
// create array
Integer[] array = new Integer[] {
        1,
        2,
        3
};
// use pipeline
boolean result = Pipeline.from(array)
        .contains(new Integer(2));
// result is true