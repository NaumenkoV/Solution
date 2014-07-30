package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Applicant does not exist")
public class ApplicantDoesNotExistException extends EntityException {
    public ApplicantDoesNotExistException(){}

    public ApplicantDoesNotExistException(String message) {
        super(message);
    }
}
