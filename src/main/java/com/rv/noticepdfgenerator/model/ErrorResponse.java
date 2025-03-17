package com.rv.noticepdfgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private int status;

    private String message;

    private String timestamp;

    private String path;

    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.path = path;
    }

}
