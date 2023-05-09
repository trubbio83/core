package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Function;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;

@Component
public class FunctionConverter implements Converter<Function, FunctionDTO> {
    @Override
    public FunctionDTO convert(Function function) throws CustomException {
        return FunctionDTO.builder()
                .id(function.getId())
                .name(function.getName())
                .build();
    }

    @Override
    public Function reverseConvert(FunctionDTO functionDTO) throws CustomException {
        return Function.builder().name(functionDTO.getName()).build();
    }

}