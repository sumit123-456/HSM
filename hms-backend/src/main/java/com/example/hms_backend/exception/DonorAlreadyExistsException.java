package com.example.hms_backend.exception;

public class DonorAlreadyExistsException extends RuntimeException{
    public DonorAlreadyExistsException(String message){
        super(message);
    }
}
