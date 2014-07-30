package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Invalid user ID")
public class InvalidUserIDException extends EntityException {
    public InvalidUserIDException(String message){
        super(message);
    }

    public InvalidUserIDException(){
    }

}
