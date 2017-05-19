```
Integer[] arr = {4,1,5,8,2};
Integer[] result = Pipeline.from(arr)
        .take(3)
        .toArray(Integer.class);
//result is {4,1,5}