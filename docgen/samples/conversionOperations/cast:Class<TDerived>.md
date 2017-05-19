```
Integer[] arr = {2,4,5,6};
Number[] result = Pipeline.from(arr1)
        .cast(Number.class).toArray(Number.class);
//result is an array of Numbers with value{2,4,5,6}