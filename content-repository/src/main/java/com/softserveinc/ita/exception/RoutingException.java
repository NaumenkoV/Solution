package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for checking routing problems in request paths in RepositoryController

 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You failed to upload because of invalid path:" +
                                                         " must contain %applicant% or %user% in it.")
public class RoutingException extends EntityException {
    public RoutingException() {
    }

    public RoutingException(String message) {
        super(message);
    }
}
