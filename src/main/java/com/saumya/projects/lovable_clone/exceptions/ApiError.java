package com.saumya.projects.lovable_clone.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

/*
 * Records are
 * - final by design: cannot be extended
 * - immutable
 * - need no boilerplate, getters
 * - auto-generated constructor
 * - auto-generated equals() and hashCode()
 * - auto-generated toString()
 * - ideal for DTOs.
 */
public record ApiError(
        HttpStatus status,
        String message,
        Instant timeStamp,
        @JsonInclude(JsonInclude.Include.NON_NULL)  // errors field will be shown only if not null 
        List<ApiFieldError> fieldErrors
) {
    public ApiError(HttpStatus status, String message) {
        this(status, message, Instant.now(), null);
    }

    public ApiError(HttpStatus status, String message, List<ApiFieldError> fieldErrors) {
        this(status, message, Instant.now(), fieldErrors);
    }
}

record ApiFieldError(String field, String message) {}
