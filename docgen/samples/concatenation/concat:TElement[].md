```
Integer[] arr1 = {0,1,2,3,4,5};
Integer[] arr2 = {6,7,8};
Interger[] result = Pipeline.from(arr1)
        .concat(arr2)
        .toArray(Integer.class);
//result is {0,1,2,3,4,5,6,7,8}
