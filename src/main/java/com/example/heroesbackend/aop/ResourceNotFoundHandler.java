package com.example.heroesbackend.aop;

import com.example.heroesbackend.exceptions.ResourceNotFoundException;
import com.example.heroesbackend.utils.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ResourceNotFoundHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerExistsByName(ResourceNotFoundException exception) {

        ValidationResponse response = new ValidationResponse(exception.getMessage(),
                HttpStatus.BAD_REQUEST);

        return response.getResponse();
    }
}
