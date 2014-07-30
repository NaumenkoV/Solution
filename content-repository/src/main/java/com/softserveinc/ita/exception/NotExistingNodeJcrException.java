package com.softserveinc.ita.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for handling bad requests (when requested Node doesn't exist),
 * is needed when we need to throw an exception
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request - Such node doesn't exist:")
public class NotExistingNodeJcrException extends JcrException {
    public NotExistingNodeJcrException() {
    }

    public NotExistingNodeJcrException(String message) {
        super(message);
    }
}
