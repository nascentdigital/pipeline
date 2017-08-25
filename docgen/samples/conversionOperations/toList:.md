```
// create array
Integer[] arr = new Integer[] {
        1,
        2,
        3,
        4,
        5
};
// use pipeline
List<Integer> result = Pipeline.from(arr)
        .toList();
// result is a list of Integers with value{1, 2, 3, 4, 5}