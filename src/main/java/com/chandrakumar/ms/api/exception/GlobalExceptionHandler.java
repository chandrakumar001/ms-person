package com.chandrakumar.ms.api.exception;

import com.chandrakumar.ms.api.exception.model.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public final ResponseEntity<ApiError> handleHttpMediaTypeNotAcceptableException(
            final HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException,
            final WebRequest webRequest) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(httpMediaTypeNotAcceptableException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(HttpHeaderException.class)
    public final ResponseEntity<ApiError> handleHttpHeaderException(
            final HttpHeaderException httpHeaderException) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(httpHeaderException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(FieldValidationException.class)
    public final ResponseEntity<ApiError> handleFieldValidationException(
            final FieldValidationException fieldValidationException) {

        final ApiError apiError = ApiError.builder()
                .type(BAD_REQUEST.getReasonPhrase())
                .code(BAD_REQUEST.value())
                .message(fieldValidationException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public final ResponseEntity<ApiError> handleNoRecordFoundException(
            final NoRecordFoundException noRecordFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(OK.getReasonPhrase())
                .code(OK.value())
                .message(noRecordFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiError> handleResourceNotFoundException(
            final ResourceNotFoundException resourceNotFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(NOT_FOUND.getReasonPhrase())
                .code(NOT_FOUND.value())
                .message(resourceNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyFoundException.class)
    public final ResponseEntity<ApiError> handleResourceAlreadyFoundException(
            final ResourceAlreadyFoundException resourceAlreadyFoundException) {

        final ApiError apiError = ApiError.builder()
                .type(CONFLICT.getReasonPhrase())
                .code(CONFLICT.value())
                .message(resourceAlreadyFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, CONFLICT);
    }
}
