package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for Jackrabbit RepositoryException handling (converting),
 * is needed when some unexpected exception occurs in JackRabbit implementation
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "This is not your fault... Some JCR trouble.")
public class JcrException extends EntityException {
    public JcrException() {
    }

    public JcrException(String message) {
        super(message);
    }
}
