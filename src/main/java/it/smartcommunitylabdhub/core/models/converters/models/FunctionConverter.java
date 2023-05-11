package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Function;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;

@Component
public class FunctionConverter implements Converter<FunctionDTO, Function> {

    @Override
    public Function convert(FunctionDTO functionDTO) throws CustomException {
        return Function.builder().name(functionDTO.getName()).build();
    }

    @Override
    public FunctionDTO reverseConvert(Function function) throws CustomException {
        return FunctionDTO.builder()
                .id(function.getId())
                .name(function.getName())
                .build();
    }

}