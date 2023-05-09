package it.smartcommunitylabdhub.core.models.converters;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.converters.interfaces.ConverterFactory;

@Component
public class ConverterFactoryImpl implements ConverterFactory {

    private final Map<String, Supplier<Converter<?, ?>>> converterRegistry = new HashMap<>();

    public void registerConverter(String kind, Supplier<Converter<?, ?>> converterSupplier) {
        converterRegistry.put(kind, converterSupplier);
    }

    @Override
    public Converter<?, ?> getConverter(String kind) {
        Supplier<Converter<?, ?>> converterSupplier = converterRegistry.get(kind);
        if (converterSupplier == null) {
            throw new IllegalArgumentException("Unknown kind: " + kind);
        }
        return converterSupplier.get();
    }
}
