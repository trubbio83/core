package it.smartcommunitylabdhub.core.models.converters.interfaces;

import it.smartcommunitylabdhub.core.exception.CustomException;

public interface Converter<T, R> {
    R convert(T input) throws CustomException;

    T reverseConvert(R input) throws CustomException;
}
