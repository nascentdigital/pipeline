```
Integer[] arr = {2,4,6,8};
boolean result = Pipeline.from(arr)
        .all(n->n%2==0);
//result is true