```
List<Integer> lst1 = Arrays.asList(0,1,2,3,4);
List<Integer> result = Pipeline.from(lst1)
                .toList();
//result is {0,1,2,3,4}