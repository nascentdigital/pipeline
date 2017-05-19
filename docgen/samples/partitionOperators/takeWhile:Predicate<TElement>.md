```
Integer[] arr = {1,2,3,4,5};
Integer[] result = Pipeline.from(arr)
        .takeWhile(n -> n%2==0)
        .toArray(Integer.class);
//result is {2,4}