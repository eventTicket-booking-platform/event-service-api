package com.ec7205.event_hub.event_service_api.adviser;


import com.ec7205.event_hub.event_service_api.exception.BadRequestException;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.exception.InvalidStatusTransitionException;
import com.ec7205.event_hub.event_service_api.exception.ResourceNotFoundException;
import com.ec7205.event_hub.event_service_api.utils.StandardResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new StandardResponseDto(404, ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({BadRequestException.class, InvalidStatusTransitionException.class})
    public ResponseEntity<StandardResponseDto> handleBadRequestExceptions(RuntimeException ex) {
        return new ResponseEntity<>(
                new StandardResponseDto(400, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardResponseDto> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                new StandardResponseDto(409, ex.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new StandardResponseDto(400, "Validation failed", validationErrors),
                HttpStatus.BAD_REQUEST
        );
    }
}
