package com.example.heroesbackend.aop;

import com.example.heroesbackend.utils.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class HttpMessageNotReadableHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handlerNoBody(HttpMessageNotReadableException exception) {

        ValidationResponse response = new ValidationResponse(exception.getMostSpecificCause().getMessage(),
                HttpStatus.BAD_REQUEST);

        return response.getResponse();
    }

}
