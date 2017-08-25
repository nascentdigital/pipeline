```
List<Integer> lst1 = Arrays.asList(0, 1, 2, 3, 4);
List<Integer> lst2 = Arrays.asList(5, 6, 7, 8);
List<Integer> result = Pipeline.from(lst1)
               .concat(lst2)
               .toList();
// result is {0, 1, 2, 3, 4, 5, 6, 7, 8}