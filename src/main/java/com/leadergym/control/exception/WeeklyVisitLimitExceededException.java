package com.leadergym.control.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.FORBIDDEN, reason = "Member has exceeded the weekly visit limit")
public class WeeklyVisitLimitExceededException extends RuntimeException {
        public WeeklyVisitLimitExceededException(String message) {
            super(message);
        }
}
