package com.example.heroesbackend.dto.inputs;

import com.example.heroesbackend.enums.Publisher;
import com.example.heroesbackend.validations.annotations.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroDto {

    private Long id;

    @NotBlank
    @Size(min = 2)
    private String name;

    @EnumValidator(enumClass = Publisher.class, message = "The publisher is invalid")
    private String publisher;


    @NotBlank
    private String alterEgo;

    @NotBlank
    private String firstApparition;

    @NotNull
    @Size(min = 1, message = "You must enter at least one character")
    private List<String> characters;

    private String urlAvatar;

}
