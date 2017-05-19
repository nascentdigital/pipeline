```
Integer[] arr1 = {1,2,3,4,5};
List<Integer> lst2 = Arrays.asList(2,4,5,8);
Integer[] result = Pipeline.from(arr1)
        .intersect(lst2)
        .toArray(Integer.class);
//result is {2,4,5}