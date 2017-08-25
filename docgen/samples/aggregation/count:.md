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
int result = Pipeline.from(arr)
        .count();
// result is 5