package com.softserveinc.ita.entity.exceptions;

/**
 * This class is for the exception messages in order to pass them through the program like a Json. Example: com/softserveinc/ita/controller/UserController.java:77
 */
public class ExceptionJSONInfo {
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String message) {
        this.reason = message;
    }

    public ExceptionJSONInfo(String reason) {
        this.reason = reason;
    }

    public ExceptionJSONInfo() {
    }
}
