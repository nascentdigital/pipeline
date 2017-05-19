```
List<Integer> lst = Arrays.asList(1,2,3,4,5);
Integer[] result = Pipeline.from(lst)
        .toArray(Integer.class);
//result is an array of Integers with value {1,2,3,4,5}