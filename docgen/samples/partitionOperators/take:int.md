```
// create array
Integer[] array = new Integer[] {
        4,
        1,
        5,
        8,
        2
};
// use pipeline
Integer[] result = Pipeline.from(array)
        .take(3)
        .toArray(Integer.class);
//result is {4, 1, 5}