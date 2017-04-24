# Pipeline
Fast and flexible collection manipulation for Android / Java.

```java
String[] petNames = Pipeline.from(pets)
    .where(p -> p.age < 15)
    .map(p -> p.name)
    .toArray();
```


## Installation
```groovy
dependencies {

  // latest one on maven central
  compile 'com.nascentdigital:pipeline:1.0.0'
}
```


## Development
Ensure that you pull the local repository to a folder named `/<project dir>/nascent-pipeline`.






## Roadmap
Some of the upcoming features:
- `contains()`: Determine if a sequence contains a value.
- `join(separator)`: Joins the values into a string using the specified separator and the default 
    `toString()` implementation for string elements.
- `distinct(v -> v.target)`: Additional distinction operator that takes a selector to specify 
    property to calculate distiction.
- `skipWhile(e)` and `takeWhile(e)`: Skip or take values while an expression is true.
- `repeat(e, count)`: Create a sequence by repeating a value.
- `intersect()`: Similar to concat, but only return intesection of supersequence with subsequence.
- `union()`: Similar to concat, but only doesn't repeat values (i.e. distinctly concatenates).
- `orderBy()` and `thenBy()`: Sorts the sequence based on a specified selector.
- `sum()` and `average()`: Caclulate the aggregate sum / average of a sequence.
- `min()` and `max()`: Caclulate the 'smallest' or 'largest' element of a sequence.
- `<sequence1>.zip(sequence2, j)`: Combines sequence1 and sequence2 using a join expression.
- `reverse()`: Reverse the order of elements within a sequence.
- Concurrent processing of pipeline steps (where possible).