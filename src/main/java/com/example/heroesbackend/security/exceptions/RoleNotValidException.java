package com.example.heroesbackend.security.exceptions;

public class RoleNotValidException extends RuntimeException {

    public RoleNotValidException() {
        super("You are entering an invalid role");
    }
}
