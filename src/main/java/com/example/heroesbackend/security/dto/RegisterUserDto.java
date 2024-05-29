package com.example.heroesbackend.security.dto;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
public class RegisterUserDto {

    @NonNull
    @NotBlank
    @Length(min = 4, max = 200)
    private String name;

    @NonNull
    @NotBlank
    @Length(min = 2, max = 20)
    private String username;

    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    @Length(min = 6, max = 16)
    private String password;

    private Set<RoleDto> roles = new HashSet<>();
}
