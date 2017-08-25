```
// create list
List<Integer> lst1 = Arrays.asList(0, 1, 2, 3, 4);
// use pipeline
Pipeline result = Pipeline.from(lst1);
// result is a Pipeline object with {0, 1, 2, 3, 4} as the initial sequence source