package com.nascentdigital.pipeline;


public interface Predicate<TInput> {

    boolean predicate(TInput value);
}
