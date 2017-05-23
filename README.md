# Pipeline
Fast and flexible collection manipulation for Android / Java.

```java
class Person {
    String name;
    int age;
}

class Family { 
    Person mom;
    Person dad;
    List<Person> children;
}

// create list of families
List<Familiy> families = ...;
```

```java
// get all single mom's
List<Person> singleMothers = Pipeline.from(families)
    .where(f -> f.dad == null)
    .map(f -> f.mom)
    .toList();
```

```java
// get all unique teenager names
List<String> uniqueTeenNames = Pipeline.from(families)
    .flatMap(f -> f.children)
    .where(c -> c.age >= 13 && c.age <= 19)
    .map(c -> c.name)
    .distinct()
    .toList();
```


## Installation

1. Add the following to your build.gradle:
```groovy
dependencies {
    compile 'com.nascentdigital:pipeline:1.3.0'
}
```

2. On Android, install the [Retrolambda](https://github.com/evant/gradle-retrolambda) plugin for a much cleaner syntax.





## Roadmap
Some of the upcoming features:
- `contains()`: Determine if a sequence contains a value.
- `join(separator)`: Joins the values into a string using the specified separator and the default 
    `toString()` implementation for string elements.
- `skipWhile(e)` and `takeWhile(e)`: Skip or take values while an expression is true.
- `repeat(e, count)`: Create a sequence by repeating a value.
- `intersect()`: Similar to concat, but only return intesection of supersequence with subsequence.
- `union()`: Similar to concat, but only doesn't repeat values (i.e. distinctly concatenates).
- `orderBy()` and `thenBy()`: Sorts the sequence based on a specified selector.
- `<sequence1>.zip(sequence2, j)`: Combines sequence1 and sequence2 using a join expression.
- `reverse()`: Reverse the order of elements within a sequence.
- Concurrent processing of pipeline steps (where possible).