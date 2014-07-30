package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is empty")
public class EmptyUserException extends EntityException {

    public EmptyUserException(String message){
        super(message);
    }

    public EmptyUserException(){
    }
}
