```
// create array 
Double[] array = new Double[] {
        8.0,
        9.0,
        0.2
};
// use pipeline
Double result = Pipeline.from(array)
        .sumDoubles(i -> i);
// result is 17.2