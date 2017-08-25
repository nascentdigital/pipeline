```
// create initial value
int initial = 5;
// create array
Integer[] array = new Integer[] {
        1,
        2,
        3,
        4
};
// use pipeline
int result = Pipeline.from(array)
        .reduce((total, i) -> i == null ? total : total + i, initial);
// result is 15
