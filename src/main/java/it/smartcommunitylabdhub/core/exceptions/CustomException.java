package it.smartcommunitylabdhub.core.exceptions;

import java.lang.RuntimeException;

public class CustomException extends RuntimeException {
    private Exception innerException;

    public CustomException(String message, Exception innerException) {
        super(message);
        this.innerException = innerException;
    }

    public Exception getInnerException() {
        return innerException;
    }
}
