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
        .firstOrDefault(n -> n%2 != 0);
// result is 5