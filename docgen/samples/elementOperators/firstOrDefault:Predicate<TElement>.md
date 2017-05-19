```
Integer[] arr1 = {2,4,5,6};
Integer result1 = Pipeline.from(arr1)
        .firstOrDefault(n -> n%2!=0);

Integer[] arr2 = {2,4,6,8};
Integer result2 = Pipeline.from(arr2)
        .firstOrDefault(n -> n%2!=0);
//result1 is 5 and result2 is null        