package com.example.heroesbackend.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
public class LoginUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
}
