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
        .where(n -> n%2 != 0)
        .toArray(Integer.class);
// result is {1, 3, 5}