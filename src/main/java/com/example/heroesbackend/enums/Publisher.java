package com.example.heroesbackend.enums;

import lombok.Getter;

@Getter
public enum Publisher {
    DC("DC COMICS"), MARVEL("DC COMICS");

    private final String name;

    Publisher(String name) {
        this.name = name;
    }
}
