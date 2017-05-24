```
String [] arr = {"a", "b", "c"};
String result = Pipeline.from(arr).join(" ");
//result becomes "a b c"
