package com.softserveinc.ita.exception.impl;

import com.softserveinc.ita.exception.GroupException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Group with this name is already exists")
public class GroupWithThisNameIsAlreadyExists extends GroupException {

    public GroupWithThisNameIsAlreadyExists() {
    }

    public GroupWithThisNameIsAlreadyExists(String message) {
        super(message);
    }
}
