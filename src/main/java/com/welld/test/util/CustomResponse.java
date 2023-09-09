package com.welld.test.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse {
    private String message;

    public CustomResponse(String message) {
        this.message = message;
    }

    }


