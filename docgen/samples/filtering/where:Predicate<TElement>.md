```
Integer [] arr = {1,2,3,4,5};
arr = Pipeline.from(arr)
        .where(n->n%2!=0)
        .toArray(Integer.class);
//returns all the odd numbers in arr
//arr is {1,3,5}