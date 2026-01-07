package co.devskills.springbootboilerplate.handler;

import java.time.Instant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import co.devskills.springbootboilerplate.error.NotFoundException;
import co.devskills.springbootboilerplate.dto.ApiError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.*;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var fields = ex.getFieldErrors().stream()
                .map(e -> new ApiError.FieldError(e.getField(), e.getDefaultMessage()))
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), fields);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidEnum(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                new ApiError(
                        Instant.now(),
                        400,
                        "Bad Request",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status, String message, String path, List<ApiError.FieldError> fields
    ) {
        return ResponseEntity.status(status).body(
                new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path, fields)
        );
    }
}