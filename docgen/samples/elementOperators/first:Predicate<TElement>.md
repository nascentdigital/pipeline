```
Integer[] arr = {2,4,5,6,7};
Integer result = Pipeline.from(arr)
        .first(n -> n%2 !=0);
//result is 5