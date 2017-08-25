```
// create arrays
Integer[] array1 = new Integer[] {
        0,
        1,
        2,
        3,
        4,
        5
};
Integer[] array2 = new Integer[] {
        6,
        7,
        8
};
// use pipeline
Interger[] result = Pipeline.from(array1)
        .concat(array2)
        .toArray(Integer.class);
// result is {0, 1, 2, 3, 4, 5, 6, 7, 8}
