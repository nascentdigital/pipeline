package com.nascentdigital.pipeline;

/**
 * Aggregates and individual value into an aggregate.
 *
 * @param <TInput> The type of input values to the function.
 * @param <TOutput> The output type of the new aggregate, as well as the input value of the original
 *                 aggregate before execution.
 */
public interface Aggregator<TInput, TOutput> {

    TOutput aggregate(TOutput aggregate, TInput element);
}
