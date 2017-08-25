```
List<List<Integer>> lst = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,6));
List<Integer> result = Pipeline.from(lst)
        .flatMap(n->Pipeline.from(n.subList(0,2)))
        .toList();
// creates a new list from the first two elements of each sublist
// result is {1,2,4,5}