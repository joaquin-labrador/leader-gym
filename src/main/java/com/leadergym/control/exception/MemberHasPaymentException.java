package com.leadergym.control.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST, reason = "The member has an active payment and cannot be deleted.")
public class MemberHasPaymentException extends RuntimeException {
    public MemberHasPaymentException(String message) {
        super(message);
    }
}
