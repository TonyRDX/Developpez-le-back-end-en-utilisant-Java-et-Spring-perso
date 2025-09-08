package com.openclassrooms.P3.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String msg) { super(msg); }
}

