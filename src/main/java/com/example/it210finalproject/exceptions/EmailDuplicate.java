package com.example.it210finalproject.exceptions;

public class EmailDuplicate extends RuntimeException {
    public EmailDuplicate(String message) {
        super(message);
    }
}
