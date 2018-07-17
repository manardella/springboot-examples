package com.example.demo.controllers;

import com.example.demo.common.AppConstants;
import com.example.demo.controllers.hellooperations.NameIsNotInTheListException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.example.demo.controllers")
public class GenericControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        final GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .errorCode(AppConstants.UNKNOWN_ERROR)
                .errorMessage(ex.getMessage())
                .mdc(MDC.getMDCAdapter().getCopyOfContextMap().toString()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NameIsNotInTheListException.class)
    public ResponseEntity<GenericErrorResponse> notFoundException(final NameIsNotInTheListException e) {
        final GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .errorCode(AppConstants.ERROR_NAME_IS_NOT_IN_THE_LIST)
                .errorMessage(e.getMessage())
                .mdc(MDC.getMDCAdapter().getCopyOfContextMap().toString()).build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
