package br.com.nova.api.escola.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * exceção lançada quando é buscado um recurso obrigatório e não é encontrado
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends GenericException {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Object details) {
        super(message, details);
    }

    public NotFoundException(String message, List<Error> errors) {
        super(message, errors);
    }

    public NotFoundException(String message) {
        super(message);
    }
}

