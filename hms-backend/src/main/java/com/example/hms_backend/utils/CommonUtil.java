package com.example.hms_backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.name());
        response.put("message", "success");
        response.put("data", data); // ✅ add actual data here
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.name());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }


}
