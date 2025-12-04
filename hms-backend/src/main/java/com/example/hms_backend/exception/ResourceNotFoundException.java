package com.example.hms_backend.exception;


// whenever any resource not found then this class method show custom message
public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
