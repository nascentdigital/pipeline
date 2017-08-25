```
// create array
String[] array = new String[] {
        "a",
        "b",
        "c"
};
String result = Pipeline.from(array)
        .join(" ");
// result becomes "a b c"
