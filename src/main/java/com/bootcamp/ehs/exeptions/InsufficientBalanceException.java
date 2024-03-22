package com.bootcamp.ehs.exeptions;

public class InsufficientBalanceException extends  RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
