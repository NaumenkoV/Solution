package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used for File validation in RepositoryController
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You failed to upload because the file has incorrect type:")
public class UnsupportedFileContentTypeException extends EntityException {
    public UnsupportedFileContentTypeException() {
    }

    public UnsupportedFileContentTypeException(String message) {
        super(message);
    }
}
