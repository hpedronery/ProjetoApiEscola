package br.com.nova.api.escola.dtos;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Classe para retorno de erro
 */
public class Error {

    private final String message;
    private final Object details;

    public Error(@NonNull String message, @Nullable Object details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetails() {
        return details;
    }
}
