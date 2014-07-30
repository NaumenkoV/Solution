package com.softserveinc.ita.exception;

import com.softserveinc.ita.error.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="There is no user with such email")
public class UserEmailNotFoundException extends EntityException {
    public UserEmailNotFoundException(String message){
        super(message);
    }

    public UserEmailNotFoundException(){
    }

}
