package it.smartcommunitylabdhub.core.services.builders;

import java.util.function.Consumer;
import java.util.function.Supplier;

import it.smartcommunitylabdhub.core.models.interfaces.BaseEntity;

public class DTOBuilder<T extends BaseEntity, U extends BaseEntity> {
    private T result;

    public DTOBuilder(Supplier<T> entitySupplier) {
        this.result = entitySupplier.get();
    }

    public DTOBuilder<T, U> with(Consumer<T> fieldSetter) {
        fieldSetter.accept(result);
        return this;
    }

    public T build() {
        return result;
    }
}