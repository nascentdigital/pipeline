```
List<Integer> lst = Arrays.asList(2,4,5,8);
Iterator iterator = Pipeline.from(lst).iterator();
while (iterator.hasNext()){
    System.out.print(iterator.next());
}
//output is 2458