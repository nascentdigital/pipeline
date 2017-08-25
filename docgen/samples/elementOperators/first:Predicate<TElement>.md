```
// create array
Integer[] array = new Integer[] {
        2,
        4,
        5,
        6,
        7
};
// use pipeline
Integer result = Pipeline.from(array)
        .first(n -> n%2 !=0);
// result is 5