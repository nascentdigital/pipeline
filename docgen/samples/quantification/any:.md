```
// create array
Integer[] array = new Integer[] {
        2
};
// use pipeline
boolean result = Pipeline.from(array)
        .any();
// result is true