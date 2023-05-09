package it.smartcommunitylabdhub.core.models.converters.interfaces;

public interface ConverterFactory {
    Converter<?, ?> getConverter(String kind);
}
