package com.softserveinc.ita.exception.impl;

import com.softserveinc.ita.error.EntityException;
import com.softserveinc.ita.exception.GroupException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Group does not exist")
public class GroupDoesntExistException extends GroupException {
    public GroupDoesntExistException() {
    }

    public GroupDoesntExistException(String message) {
        super(message);
    }
}
