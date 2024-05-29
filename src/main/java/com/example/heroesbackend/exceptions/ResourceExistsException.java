package com.example.heroesbackend.exceptions;


public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String name, String table) {
        super(String.format("The resource %s exists in %s", name, table));
    }
}
