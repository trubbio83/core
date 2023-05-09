package it.smartcommunitylabdhub.core.models.converters.interfaces;

public interface ConverterCommand<T, R> {
    R execute();
}