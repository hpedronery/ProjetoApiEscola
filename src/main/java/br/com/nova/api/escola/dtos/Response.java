package br.com.nova.api.escola.dtos;

import br.com.nova.api.escola.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
 * Essa classe é responsável por entregar as respostas para o retorno das requisições
 */
public class Response<T> {

    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final T data;
    private final T error;

    /**
     * Se status for entre 200 e 299 seta o atributo {@code data} com o parâmetro {@code dataOrError}
     * Caso contrário seta o atributo {@code error} com o parâmetro {@code dataOrError}
     */
    private Response(@NonNull int status, @NonNull String message, @Nullable T dataOrError) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        if (status >= 200 && status < 300) {
            this.data = dataOrError;
            this.error = null;
        } else {
            this.data = null;
            this.error = dataOrError;
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public T getError() {
        return error;
    }

    /**
     * Gera um objeto Response com body, http status e message.
     *
     * @param body    corpo da Response
     * @param status  numero do http status da Response
     * @param message message do codigo da Response
     * @param <T>     a classe do corpo da Response
     * @return objeto Response respectivo
     */
    public static <T> Response<T> of(@Nullable T body, @NonNull int status, @NonNull String message) {
        return new Response<>(status, message, body);
    }

    /**
     * Gera um objeto Response com body e http status.
     *
     * @param httpStatus http status da Response
     */
    public static <T> Response<T> of(@Nullable T body, @NonNull HttpStatus httpStatus) {
        return new Response<>(httpStatus.value(), httpStatus.getReasonPhrase(), body);
    }

    /**
     * Gera um objeto Response com o body e status
     *
     * @return o metodo responsável por enviar a Response.
     */

    public static <T> Response<T> ok(@Nullable T body) {
        return of(body, HttpStatus.OK.value(), MessageUtils.buscarMensagem("response.code200"));
    }

    /**
     * Gera um objeto Response com o body e status
     *
     * @return o metodo responsável por enviar a Response.
     */

    public static <T> Response<T> created(@Nullable T body) {
        return of(body, HttpStatus.CREATED.value(), MessageUtils.buscarMensagem("response.code201"));
    }

    /**
     * Gera um objeto Response com o body e status
     *
     * @return o metodo responsável por enviar a Response.
     */

    public static <T> Response<T> deleted() {
        return of(null, HttpStatus.NO_CONTENT.value(), MessageUtils.buscarMensagem("response.code204"));
    }

    public static <T> Response<T> badRequest(@Nullable T body) {
        return of(body, HttpStatus.BAD_REQUEST.value(), MessageUtils.buscarMensagem("response.code400"));
    }

    public static <T> Response<T> unauthorized(@Nullable T body) {
        return of(body, HttpStatus.UNAUTHORIZED.value(), MessageUtils.buscarMensagem("response.code401"));
    }

    public static <T> Response<T> forbidden(@Nullable T body) {
        return of(body, HttpStatus.FORBIDDEN.value(), MessageUtils.buscarMensagem("response.code403"));
    }

    public static <T> Response<T> notFound(@Nullable T body) {
        return of(body, HttpStatus.NOT_FOUND.value(), MessageUtils.buscarMensagem("response.code404"));
    }

    public static <T> Response<T> internalError(@Nullable T body) {
        return of(body, HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageUtils.buscarMensagem("response.code500"));
    }
}
