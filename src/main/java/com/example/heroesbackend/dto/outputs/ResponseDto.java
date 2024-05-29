package com.example.heroesbackend.dto.outputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ResponseDto {

    private Object data;
    private String message;
    private Date timestamp;

    public ResponseDto(Object data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = new Date();
    }
}

