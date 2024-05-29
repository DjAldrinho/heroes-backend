package com.example.heroesbackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidationResponse {

    private final String message;

    private final HttpStatus httpStatus;

    private final Date timestamp;

    private final Map<String, Object> body = new HashMap<>();


    public ValidationResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = new Date();
    }

    public ValidationResponse addData(String key, Object value) {
        this.body.put(key, value);
        return this;
    }


    public ResponseEntity<Map<String, Object>> getResponse() {
        this.body.put("message", this.message);
        this.body.put("timestamp", this.timestamp);
        return new ResponseEntity<>(this.body, this.httpStatus);
    }
}
