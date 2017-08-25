```
// create array
Float[] array = new Float[] {
        8.0f,
        9.0f,
        0.2f
};
// use pipeline
Float result = Pipeline.from(array)
        .maxFloat(i -> i);
// result is 9.0
