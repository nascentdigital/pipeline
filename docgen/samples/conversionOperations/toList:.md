```
Integer[] arr = {1,2,3,4,5};
List<Integer> result = Pipeline.from(arr)
        .toList();
//result is a list of Integers with value{1,2,3,4,5}