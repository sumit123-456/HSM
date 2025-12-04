package com.example.hms_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    //When it triggers: When @Valid detects invalid input in a request body.
    //What it does: Converts validation errors into a clear JSON response instead of throwing a confusing error page.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ValidationErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        List <ValidationErrorResponse> errors=new ArrayList<>();
        for(FieldError error:ex.getBindingResult().getFieldErrors())
        {
            ValidationErrorResponse validationError = new ValidationErrorResponse(error.getField(), error.getDefaultMessage());
            errors.add(validationError);
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


    // Whenever your code throws a ResourceNotFoundException, this method is triggered.
    //It prepares a map (key-value pairs) containing:
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Resource Not Found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", ex.getHttpStatus().value());
        error.put("errorCode", ex.getErrorCode());
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    //If any other exception (not specifically handled) occurs, this method catches it
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

   //max size upload handle
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("File too large! Maximum allowed size is 2MB");
    }



}
