package br.com.nova.api.escola.exceptions;

import br.com.nova.api.escola.dtos.Error;

import java.util.Collections;
import java.util.List;

/**
 * Exceção Genérica. Deve ser lançada quando algum erro desconhecido interrompeu a operação
 */

public class GenericException extends RuntimeException {

    private final transient List<Error> errors;

    public GenericException(final String message, final Throwable cause) {
        super(message, cause);
        this.errors = Collections.singletonList(new Error(message, cause.getStackTrace()));
    }

    public GenericException(final String message, final Object details) {
        super(message);
        this.errors = Collections.singletonList(new Error(message, details));
    }

    public GenericException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public GenericException(final String message) {
        this(message, message);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
