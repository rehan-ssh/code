package com.example.ecom.exceptions;

public class GiftCardDoesntExistException extends Exception{
    public GiftCardDoesntExistException(String message) {
        super(message);
    }
}
