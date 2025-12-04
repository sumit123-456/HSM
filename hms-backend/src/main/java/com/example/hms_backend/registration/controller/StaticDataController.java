package com.example.hms_backend.registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class StaticDataController {

    private final RestTemplate restTemplate;

    @GetMapping("/states")
    public ResponseEntity<?> getStates() {
        String url = "https://shubham8116.github.io/State-District-Data/sdm.json";
        String json = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(json);
    }
}
