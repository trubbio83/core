package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;

@Component
public class IntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String input) throws CustomException {
        return Integer.parseInt(input);
    }

    @Override
    public String reverseConvert(Integer input) throws CustomException {
        return input.toString();
    }

}
