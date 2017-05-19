```
Integer[] arr = {2,4,5,6};
boolean result = Pipeline.from(arr)
        .any(n->n%2!=0);
//result is true