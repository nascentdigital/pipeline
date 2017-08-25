```
// create array
Short[] array = new Short[] {
        8,
        9,
        0
};
// use pipeline
Short result = Pipeline.from(array)
        .sumShorts(i -> i);
// result is 17