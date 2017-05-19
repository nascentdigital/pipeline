```
Integer[] arr1 = {2,4,5,6};
Integer result1 = Pipeline.from(arr1)
        .firstOrDefault();
        
Integer[] arr2 = {};
Integer result2 = Pipeline.from(arr2)
        .firstOrDefault();
//result1 is 2 and result2 is null