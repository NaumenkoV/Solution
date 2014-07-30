package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for Base 64 validations in RepositoryController
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You failed to upload because the file was empty.")
public class Base64ValidationException extends EntityException {
    public Base64ValidationException() {
    }

    public Base64ValidationException(String message) {
        super(message);
    }
}
