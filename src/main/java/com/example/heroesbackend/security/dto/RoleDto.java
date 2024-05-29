package com.example.heroesbackend.security.dto;

import com.example.heroesbackend.security.enums.RolName;
import com.example.heroesbackend.validations.annotations.EnumValidator;
import lombok.Data;
import lombok.NonNull;

@Data
public class RoleDto {

    @NonNull
    @EnumValidator(enumClass = RolName.class, message = "The rol name is invalid")
    private String rolName;
}
