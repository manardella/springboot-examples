package com.example.demo.controllers.hellooperations;

public class NameIsNotInTheListException extends RuntimeException {
    NameIsNotInTheListException(String message, Exception innerException) {
        super(message, innerException);
    }
}
