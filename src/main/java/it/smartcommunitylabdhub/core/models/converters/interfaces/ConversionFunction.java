package it.smartcommunitylabdhub.core.models.converters.interfaces;

import it.smartcommunitylabdhub.core.exception.CustomException;

@FunctionalInterface
public interface ConversionFunction<S, R> {
    R convert(S source) throws CustomException;
}
