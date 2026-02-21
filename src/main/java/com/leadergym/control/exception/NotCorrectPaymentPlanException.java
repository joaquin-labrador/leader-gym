package com.leadergym.control.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Payment plan is not correct")
public class NotCorrectPaymentPlanException extends RuntimeException {
    public NotCorrectPaymentPlanException(String message) {
        super(message);
    }
}
