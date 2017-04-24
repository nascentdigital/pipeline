package com.nascentdigital.pipeline;


public interface Selector<TInput, TOutput> {

    TOutput select(TInput input);
}
