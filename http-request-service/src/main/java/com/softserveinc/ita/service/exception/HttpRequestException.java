package com.softserveinc.ita.service.exception;


public class HttpRequestException extends Exception {

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(String msg) {
        super(msg);
    }
}
