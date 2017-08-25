```
// create array
Integer[] arr = new Integer[] {
        0,
        1, 
        2, 
        3, 
        4 
};
// use pipeline
Pipeline result = Pipeline.from(arr);
// result is a Pipeline object with {0, 1, 2, 3, 4} as the initial sequence source