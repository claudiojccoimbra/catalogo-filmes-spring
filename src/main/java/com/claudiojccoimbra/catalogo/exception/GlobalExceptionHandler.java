package com.claudiojccoimbra.catalogo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ConstraintViolationException;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 — domínio/negócio
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException ex, WebRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    // 404 — delete(id) inexistente etc.
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleEmptyResult(EmptyResultDataAccessException ex, WebRequest req) {
        return build(HttpStatus.NOT_FOUND, "Recurso não encontrado", req);
    }

    // 400 — validação de body (@Valid) com Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> "%s: %s".formatted(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // 400 — validação em @RequestParam/@PathVariable (@Validated no controller)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest req) {
        String msg = ex.getConstraintViolations().stream()
                .map(v -> "%s: %s".formatted(v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // 400 — tipos errados em path/query (ex.: id=abc onde era Long)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest req) {
        String name = ex.getName();
        String required = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "tipo esperado";
        String msg = "Parâmetro '%s' inválido. Esperado %s.".formatted(name, required);
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // 400 — parâmetro obrigatório ausente
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex, WebRequest req) {
        String msg = "Parâmetro obrigatório ausente: %s".formatted(ex.getParameterName());
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // 400 — JSON malformado / valor de tipo inválido (ex.: data no formato errado)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(HttpMessageNotReadableException ex, WebRequest req) {
        String msg = "Corpo da requisição inválido.";
        Throwable cause = ex.getMostSpecificCause();
        if (cause instanceof InvalidFormatException ife && !ife.getPath().isEmpty()) {
            String field = ife.getPath().get(0).getFieldName();
            String target = ife.getTargetType() != null ? ife.getTargetType().getSimpleName() : "tipo esperado";
            msg = "Campo '%s' com valor inválido. Esperado %s.".formatted(field, target);
        }
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // 400 — IllegalArgument (regras simples de serviço)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex, WebRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    // 409 — chaves únicas/foreign key/etc.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException ex, WebRequest req) {
        return build(HttpStatus.CONFLICT, "Violação de integridade de dados", req);
    }

    // Propagar ResponseStatusException (se usada)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex, WebRequest req) {
        String msg = ex.getReason() != null ? ex.getReason() : ex.getStatusCode().toString();
        return build(HttpStatus.valueOf(ex.getStatusCode().value()), msg, req);
    }

    // Unwrap de validação dentro de transação
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiError> handleTx(TransactionSystemException ex, WebRequest req) {
        Throwable root = ex.getMostSpecificCause();
        if (root instanceof ConstraintViolationException cve) {
            String msg = cve.getConstraintViolations().stream()
                    .map(v -> "%s: %s".formatted(v.getPropertyPath(), v.getMessage()))
                    .collect(Collectors.joining("; "));
            return build(HttpStatus.BAD_REQUEST, msg, req);
        }
        log.error("Erro transacional", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", req);
    }

    // 500 — fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest req) {
        log.error("Erro não tratado", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, WebRequest req) {
        ApiError body = new ApiError(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                req.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(status).body(body);
    }
}
