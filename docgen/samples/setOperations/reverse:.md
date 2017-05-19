```
List<Integer> lst = Arrays.asList(2,4,5,8);
Integer[] result = Pipeline.from(lst)
        .reverse()
        .toArray(Integer.class);
//result is {8,5,4,2}