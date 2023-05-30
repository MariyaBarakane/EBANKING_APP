package com.example.ebanking_backend.exception;

public class BalanceNotSufficentException extends Throwable {
    public BalanceNotSufficentException(String message) {
        super(message);
    }
}
