package com.softserveinc.ita.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom entity exception should extend this class. They all should define @ResponseStatus(value = "...", reason = "...";
 * If they don't, default FAILED_DEPENDENCY exception will be thrown.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Entity Exception. Failed dependency")
public class EntityException extends Exception {
    public EntityException(){}
    public EntityException(String message){
        super(message);
    }
}
