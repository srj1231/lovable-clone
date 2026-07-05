package com.saumya.projects.lovable_clone.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
                ex.getResourceName() + " with id " + ex.getResourceId() + " not found.");
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // a really cool way to show errors!
        /*
         * {
                "status": "400 BAD_REQUEST",
                "message": "Input validation failed.",
                "timeStamp": "2026-07-05T12:36:26.360544Z",
                "fieldErrors": [
                    {
                        "field": "name",
                        "message": "must not be blank"
                    }
                ]
            }
         */
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new ApiFieldError(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Input validation failed.", errors);
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}

/*
 * @RestControllerAdvice combines @ControllerAdvice and @ResponseBody.
 * It centralizes exception handling for all controllers,
 * automatically converts the exception to a JSON response.
 *
 * Benefits:
 * - Centralized error handling: clean controllers
 * - Consistent error format: ApiError in our case
 */
