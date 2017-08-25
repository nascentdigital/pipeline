```
// create array 
Integer[] arr = {
        1,
        2,
        3,
        4,
        5,
        6
};
// use pipeline
int result = Pipeline.from(arr)
        .count(n -> n%2 == 0);
// result is 3