package com.example.ecom.exceptions;

public class OrderDoesNotBelongToUserException extends Exception {
    public OrderDoesNotBelongToUserException(String message) {
        super(message);
    }
}
