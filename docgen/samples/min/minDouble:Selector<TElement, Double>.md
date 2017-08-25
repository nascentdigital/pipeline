```
// create array 
Double[] array = new Double[] {
        8.0,
        9.0,
        0.2
};
// use pipeline
Double result = Pipeline.from(array)
        .minDouble(i -> i);
// result is 0.2 