```
// create array
Integer[] array = new Integer[] {
        1,
        2,
        3,
        4,
        5
};
// use pipeline
Integer[] result = Pipeline.from(array)
        .skipWhile(n -> n%2 != 0)
        .toArray(Integer.class);
// result is {2, 3, 4, 5}