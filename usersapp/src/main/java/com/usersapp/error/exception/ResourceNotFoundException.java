package com.usersapp.error.exception;

/**
 * Custom exception used to create 404 error response
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
