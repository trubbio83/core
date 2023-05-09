package it.smartcommunitylabdhub.core.models.converters;

import java.util.Collection;
import java.util.stream.Collectors;

import it.smartcommunitylabdhub.core.models.converters.interfaces.ConverterCommand;

public class ConversionUtils {
    public static <S, T> T convert(S source, CommandFactory commandFactory, String kind) {
        ConverterCommand<S, T> command = commandFactory.createConvertCommand(kind, source);
        return command.execute();
    }

    public static <S, T> T reverse(S source, CommandFactory commandFactory, String kind) {
        ConverterCommand<S, T> command = commandFactory.createReverseConvertCommand(kind, source);
        return command.execute();
    }

    public static <S, T> Collection<T> convertIterable(Collection<S> source, CommandFactory commandFactory, String kind,
            Class<T> returnType) {
        return source.stream().map(element -> {
            ConverterCommand<S, T> command = commandFactory.createConvertCommand(kind, element);
            return command.execute();
        }).collect(Collectors.toList());
    }

    public static <S, T> Collection<T> reverseIterable(Collection<S> source, CommandFactory commandFactory,
            String kind,
            Class<T> returnType) {
        return source.stream().map(element -> {
            ConverterCommand<S, T> command = commandFactory.createReverseConvertCommand(kind, element);
            return command.execute();
        }).collect(Collectors.toList());
    }

}
