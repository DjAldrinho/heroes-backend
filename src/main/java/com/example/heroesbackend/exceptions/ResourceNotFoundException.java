package com.example.heroesbackend.exceptions;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource dont exist");
    }

    public ResourceNotFoundException(String name, String fieldName, String table) {
        super(String.format("The resource for %s: %s not found in %s", fieldName, name, table));
    }
}
