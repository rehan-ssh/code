package com.example.bmsbookticket.exceptions;

public class UnAuthorizedAccessException extends Exception{
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
