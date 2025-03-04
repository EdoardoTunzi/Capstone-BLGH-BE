package com.example.Capstone_BLGH_BE.model.exceptions;

public class UsernameDuplicateException extends RuntimeException {
    public UsernameDuplicateException(String message) {
        super(message);
    }
}
