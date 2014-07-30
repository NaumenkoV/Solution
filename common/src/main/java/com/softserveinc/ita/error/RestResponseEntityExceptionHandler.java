package com.softserveinc.ita.error;

import com.softserveinc.ita.entity.exceptions.ExceptionJSONInfo;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = Logger.getLogger(getClass());

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        List<ObjectError> bodyOfResponse = ex.getBindingResult().getAllErrors();
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     *
     * @param exception -> Controller specific exception.
     * @return ExceptionJSONInfo -> (JSON) Reason of exception transformed into Json format.
     */
    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ExceptionJSONInfo> handleApplicantException(EntityException exception, HttpServletRequest request){
        HttpStatus httpStatus = exception.getClass().getAnnotation(ResponseStatus.class).value();  //get response status of the exception class
        String exceptionReason = exception.getClass().getAnnotation(ResponseStatus.class).reason();  // get reason of the exception class
        ExceptionJSONInfo exceptionInfo = new ExceptionJSONInfo();
        exceptionInfo.setReason(exceptionReason);
        if(exception.getMessage() != null) {
            exceptionInfo.setReason(exceptionReason + " " + exception.getMessage());
        }
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(" ********START_ERROR_SNIPPET********\n");
        logMessage.append(" Error during the request to the URL " + request.getRequestURI() + ".\n");
        logMessage.append(" Reason is: " + exceptionInfo.getReason() + ".\n");
        logMessage.append(" HttpStatus is " + httpStatus.value() + ".\n");
        logMessage.append(" ********END_ERROR_SNIPPET********\n");
        logger.error(logMessage);
        return new ResponseEntity<>(exceptionInfo, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionJSONInfo> handleApplicantException(Exception exception, HttpServletRequest request){
        String exceptionReason = "Internal Server Error";
        ExceptionJSONInfo exceptionInfo = new ExceptionJSONInfo();
        exceptionInfo.setReason(exceptionReason);
        if(exception.getMessage() != null) {
            exceptionInfo.setReason(exceptionReason + " " + exception.getMessage());
        }
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(" ********START_ERROR_SNIPPET********\n");
        logMessage.append(" Error during the request to the URL " + request.getRequestURI() + ".\n");
        logMessage.append(" Reason is: " + exceptionInfo.getReason() + ".\n");
        logMessage.append(" ********END_ERROR_SNIPPET********\n");
        logger.error(logMessage);
        return new ResponseEntity<>(exceptionInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}