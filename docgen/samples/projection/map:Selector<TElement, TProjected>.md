```
Integer [] arr = {1,2,3,4,5};
arr = Pipeline.from(arr)
        .map(n->n*2)
        .toArray(Integer.class);
//arr is {2,4,6,8,10}