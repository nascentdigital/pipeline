```
// create array
Integer[] array = new Integer[] {
        1,
        2,
        5,
        1,
        3,
        2
};
// use pipeline
Integer[] result = Pipeline.from(array)
        .distinct()
        .toArray(Integer.class);
// result is {1, 2, 5, 3}