```
Integer[] arr = { 0, 1, 2, 3, 4, 5 };
Integer[] result = Pipeline.from(arr)
           .toArray(Integer.class);
//result is {0,1,2,3,4,5}