package com.example.it210finalproject.exceptions;

public class EmailDuplicateRegister extends RuntimeException {
    public EmailDuplicateRegister(String message) {
        super(message);
    }
}
