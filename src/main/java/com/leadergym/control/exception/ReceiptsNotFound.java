package com.leadergym.control.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Receipts not found for the specified member")
public class ReceiptsNotFound extends RuntimeException {
    public ReceiptsNotFound(String message) {
        super(message);
    }
}
