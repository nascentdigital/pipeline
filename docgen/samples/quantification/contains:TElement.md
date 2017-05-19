```
Integer[] arr = {1,2,3};
boolean result = Pipeline.from(arr)
        .contains(new Integer(2));
//result is true