package com.example.splitwise.exceptions;

public class UnAuthorizedAccessException extends Exception{
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
