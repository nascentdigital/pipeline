```
// create array
Long[] array = new Long[] {
        8L,
        9L,
        0L
};
// use pipeline
Long result = Pipeline.from(array)
        .sumLongs(i -> i);
// result is 17