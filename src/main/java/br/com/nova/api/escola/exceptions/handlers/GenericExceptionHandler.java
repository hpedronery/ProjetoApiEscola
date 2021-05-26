package br.com.nova.api.escola.exceptions.handlers;

import br.com.nova.api.escola.dtos.Error;
import br.com.nova.api.escola.dtos.Response;
import br.com.nova.api.escola.exceptions.NotFoundException;
import br.com.nova.api.escola.exceptions.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        Response<List<Error>> body = Response.badRequest(Collections.singletonList(new Error(ex.getLocalizedMessage(), null)));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    protected Response<List<Error>> handleNotFoundException(final NotFoundException ex) {
        return Response.notFound(ex.getErrors());
    }

    @ExceptionHandler(ValidationException.class)
    protected Response<List<Error>> handleValidationException(final ValidationException ex) {
        return Response.badRequest(ex.getErrors());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected Response<List<Error>> handleConstraintViolation(final ConstraintViolationException ex) {
        List<Error> errorList = ex.getConstraintViolations().stream().map(this::mapConstrainViolations).collect(Collectors.toList());
        return Response.badRequest(errorList);
    }

    private Error mapConstrainViolations(ConstraintViolation<?> constraintViolation) {
        return new Error(constraintViolation.getMessage(), "valor rejeitado: " + constraintViolation.getInvalidValue());
    }
}

