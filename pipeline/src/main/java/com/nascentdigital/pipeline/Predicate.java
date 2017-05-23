package com.nascentdigital.pipeline;


public interface Predicate<TInput> {

    boolean evaluate(TInput value);
}
