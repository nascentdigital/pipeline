package com.nascentdigital.pipeline.annotations;

/**
 * Created by sdedios on 2017-05-02.
 */

public enum GroupType {

    Undefined("Undefined"),
    Creation("Creation"),
    Concatenation("Concatenation"),
    Projection("Projection"),
    Filtering("Filtering"),
    PartitionOperators("Partition Operators"),
    Aggregation("Aggregation"),
    Grouping("Grouping"),
    Quantification("Quantification"),
    ElementOperators("Element Operators"),
    ConversionOperations("Converstion Operations"),
    Repetition("Repetition"),
    Reduce("Reduce"),
    Sum("Sum"),
    Min("Min"),
    Max("Max"),
    SetOperations("Set Operations"),
    InterfaceIterator("Interface Iterator");

    public final String name;

    private GroupType(String name){
        this.name = name;
    }


}
