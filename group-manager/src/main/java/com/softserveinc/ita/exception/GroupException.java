package com.softserveinc.ita.exception;


import com.softserveinc.ita.error.EntityException;

public class GroupException extends EntityException {

    public GroupException(){
    }

    public GroupException(String message){
        super(message);
    }
}
