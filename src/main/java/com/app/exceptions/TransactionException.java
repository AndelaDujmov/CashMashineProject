package com.app.exceptions;


public class TransactionException extends RuntimeException {

    public TransactionException(){
        super("Invalid transaction!");
    }
}
