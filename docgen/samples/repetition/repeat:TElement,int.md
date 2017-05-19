```
List<Integer> lst = Arrays.asList(1,2,3);
        List<Integer> result = Pipeline.from(lst)
                .repeat(new Integer(4),2)
                .toList();
//result is {4,4}