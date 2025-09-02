package com.example.shortenurl.exceptions;

public class UrlNotFoundException extends Exception{
    public UrlNotFoundException(String message) {
        super(message);
    }
}
