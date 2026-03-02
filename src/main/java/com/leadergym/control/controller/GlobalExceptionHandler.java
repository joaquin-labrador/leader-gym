package com.leadergym.control.controller;

import com.leadergym.control.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path,
            Map<String, String> fields
    ) {}

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiError> handleMemberNotFound(MemberNotFoundException ex,
                                                         jakarta.servlet.http.HttpServletRequest req) {
        // OJO: vos pedís 403 aunque lo típico sería 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiError(Instant.now(), 404, "NotFound", ex.getMessage(), req.getRequestURI(), null)
        );
    }

    @ExceptionHandler(MemberNotPayException.class)
    public ResponseEntity<ApiError> handleNotPaid(MemberNotPayException ex,
                                                  jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiError(Instant.now(), 403, "Forbidden", ex.getMessage(), req.getRequestURI(), null)
        );
    }

    @ExceptionHandler(WeeklyVisitLimitExceededException.class)
    public ResponseEntity<ApiError> handleWeeklyLimit(WeeklyVisitLimitExceededException ex,
                                                      jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiError(Instant.now(), 403, "Forbidden", ex.getMessage(), req.getRequestURI(), null)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
                                                     jakarta.servlet.http.HttpServletRequest req) {
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fields.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                new ApiError(Instant.now(), 400, "Bad Request", "Validation failed", req.getRequestURI(), fields)
        );
    }

    @ExceptionHandler(ReceiptsNotFound.class)
    public ResponseEntity<ApiError> handleReceiptsNotFound(ReceiptsNotFound ex,
                                                          jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiError(Instant.now(), 404, "Not Found", ex.getMessage(), req.getRequestURI(), null)
        );
    }

    @ExceptionHandler(DailyVisitLimitExceededException.class)
    public ResponseEntity<ApiError> handleDailyLimit(DailyVisitLimitExceededException ex,
                                                     jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiError(Instant.now(), 403, "Forbidden", ex.getMessage(), req.getRequestURI(), null)
        );
    }

    @ExceptionHandler(MemberHasPaymentException.class)
    public ResponseEntity<ApiError> handleMemberHasPayment(MemberHasPaymentException ex,
                                                          jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(Instant.now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI(), null)
        );
    }
}
