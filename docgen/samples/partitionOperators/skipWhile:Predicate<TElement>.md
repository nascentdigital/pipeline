```
Integer[] arr = {1,2,3,4,5};
Integer[] result = Pipeline.from(arr)
        //skip all the even numbers 
        .skipWhile(n -> n%2 != 0)
        .toArray(Integer.class);
//returns all the skipped elements
//result is {2,3,4,5}