package com.example.hms_backend.exception;

public class ValidationErrorResponse {

    private String field;
    private String message;

    // Constructors, getters, and setters
    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {

        return field;
    }

    public void setField(String field) {

        this.field = field;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
