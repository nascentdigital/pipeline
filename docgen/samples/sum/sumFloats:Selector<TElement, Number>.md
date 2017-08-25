```
// create array
Float[] array = new Float[] {
        8.0f,
        9.0f,
        0.2f
};
// use pipeline
Float result = Pipeline.from(array)
        .sumFloats(i -> i);
// result is 17.2
