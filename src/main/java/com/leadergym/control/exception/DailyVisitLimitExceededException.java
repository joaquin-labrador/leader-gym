package com.leadergym.control.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Daily visit limit exceeded")
public class DailyVisitLimitExceededException extends RuntimeException {
    public DailyVisitLimitExceededException(String message) {
        super(message);
    }
}
