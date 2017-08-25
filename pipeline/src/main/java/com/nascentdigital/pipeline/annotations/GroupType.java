package com.nascentdigital.pipeline.annotations;

/**
 * Created by sdedios on 2017-05-02.
 */

public enum GroupType {

    Undefined("Undefined"),
    Aggregation("Aggregation"),
    Concatenation("Concatenation"),
    ConversionOperations("Converstion Operations"),
    Creation("Creation"),
    ElementOperators("Element Operators"),
    Filtering("Filtering"),
    Grouping("Grouping"),
    InterfaceIterator("Interface Iterator"),
    Max("Max"),
    Min("Min"),
    PartitionOperators("Partition Operators"),
    Projection("Projection"),
    Quantification("Quantification"),
    Reduce("Reduce"),
    Repetition("Repetition"),
    SetOperations("Set Operations"),
    Sum("Sum");

    public final String name;

    private GroupType(String name){
        this.name = name;
    }


}
