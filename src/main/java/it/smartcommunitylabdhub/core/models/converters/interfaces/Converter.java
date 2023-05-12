package it.smartcommunitylabdhub.core.models.converters.interfaces;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;

public interface Converter<T, R> {
    R convert(T input, CommandFactory commandFactory) throws CustomException;

    T reverseConvert(R input, CommandFactory commandFactory) throws CustomException;
}
