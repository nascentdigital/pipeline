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
  compile 'com.nascentdigital:pipeline-v1:0.0'
}
```









## Roadmap
Some of the upcoming features:
- Improve the performance of the `distinct()` function to be streaming.
- `selectMany()`: Projects each element of a sequence to flattened sequence in the pipeline. 
- `orderBy()`: Sorts the sequence based on a specified selector.
- Concurrent processing of pipeline steps (where possible).