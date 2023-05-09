package it.smartcommunitylabdhub.core.models.converters.interfaces;

import it.smartcommunitylabdhub.core.exception.CustomException;

public interface Converter<S, T> {
    T convert(S source) throws CustomException;

    S convertReverse(T target) throws CustomException;
}
