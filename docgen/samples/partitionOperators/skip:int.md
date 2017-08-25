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
        .skip(3)
        .toArray(Integer.class);
// result is {8, 2}