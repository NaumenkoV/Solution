package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used for File validation in RepositoryController
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You failed to upload file because it was empty:")
public class EmptyFileException extends EntityException {
    public EmptyFileException() {
    }

    public EmptyFileException(String message) {
        super(message);
    }
}
