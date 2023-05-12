package it.smartcommunitylabdhub.core.models.converters.types;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exceptions.CustomException;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;

@Component
public class IntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String input, CommandFactory commandFactory) throws CustomException {
        return Integer.parseInt(input);
    }

    @Override
    public String reverseConvert(Integer input, CommandFactory commandFactory) throws CustomException {
        return input.toString();
    }

}
