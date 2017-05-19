```
Integer [] arr = {1,2,5,1,3,2};
arr = Pipeline.from(arr)
    .distinct()
    .toArray(Integer.class);
//arr is now {1,2,5,3}