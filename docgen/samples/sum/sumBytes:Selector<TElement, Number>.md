```
// create array
Byte [] array = new Byte[] {
        8,
        9,
        0
};
// use pipeline
Byte result = Pipeline.from(array)
        .sumBytes(i -> i.byteValue());

// result is 17