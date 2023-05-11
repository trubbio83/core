package it.smartcommunitylabdhub.core.services.factory;

import java.util.function.Consumer;
import java.util.function.Supplier;

import it.smartcommunitylabdhub.core.models.interfaces.BaseEntity;
import it.smartcommunitylabdhub.core.services.builders.DTOBuilder;

public class DTOFactory {
    public static <T extends BaseEntity, U extends BaseEntity> T createDTO(Supplier<T> entitySupplier, U entity,
            Consumer<DTOBuilder<T, U>> buildFunction) {
        DTOBuilder<T, U> builder = new DTOBuilder<>(entitySupplier);
        buildFunction.accept(builder);
        return builder.build();
    }
}