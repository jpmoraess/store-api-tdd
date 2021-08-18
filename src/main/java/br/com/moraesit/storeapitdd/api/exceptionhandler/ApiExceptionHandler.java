package br.com.moraesit.storeapitdd.api.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ApiError.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(f -> ApiError.FieldError.builder()
                .field(f.getField())
                .message(f.getDefaultMessage())
                .build()).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(400)
                        .data(LocalDateTime.now())
                        .message(ex.getMessage())
                        .fieldErrors(fieldErrors)
                        .build());
    }
}
