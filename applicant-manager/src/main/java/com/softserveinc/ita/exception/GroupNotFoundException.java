package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Group does not exists")
public class GroupNotFoundException extends EntityException {
    public GroupNotFoundException() {
        super();
    }

    public GroupNotFoundException(String s) {
        super(s);
    }
}
