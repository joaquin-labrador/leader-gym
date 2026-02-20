package com.leadergym.control.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Member has not paid the membership fee")
public class MemberNotPayException extends RuntimeException {
    public MemberNotPayException(String message) {
        super(message);
    }
}
