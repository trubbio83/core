package it.smartcommunitylabdhub.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.ConverterFactoryImpl;
import it.smartcommunitylabdhub.core.models.converters.interfaces.ConverterFactory;
import it.smartcommunitylabdhub.core.models.converters.models.ArtifactConverter;
import it.smartcommunitylabdhub.core.models.converters.models.CBORConverter;
import it.smartcommunitylabdhub.core.models.converters.models.FunctionConverter;
import it.smartcommunitylabdhub.core.models.converters.models.IntegerConverter;
import it.smartcommunitylabdhub.core.models.converters.models.ProjectConverter;
import it.smartcommunitylabdhub.core.models.converters.models.RunConverter;
import it.smartcommunitylabdhub.core.models.converters.models.WorkflowConverter;

@Configuration
public class ConverterConfig {

    @Bean
    ConverterFactory converterFactory() {
        ConverterFactoryImpl factory = new ConverterFactoryImpl();

        // Register converter
        factory.registerConverter("function", FunctionConverter::new);
        factory.registerConverter("artifact", ArtifactConverter::new);
        factory.registerConverter("workflow", WorkflowConverter::new);
        factory.registerConverter("project", ProjectConverter::new);
        factory.registerConverter("cbor", CBORConverter::new);
        factory.registerConverter("integer", IntegerConverter::new);
        factory.registerConverter("run", RunConverter::new);

        return factory;
    }

    @Bean
    CommandFactory commandFactory(ConverterFactory converterFactory) {
        return new CommandFactory(converterFactory);
    }

}
