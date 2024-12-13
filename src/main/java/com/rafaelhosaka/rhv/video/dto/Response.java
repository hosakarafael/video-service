package com.rafaelhosaka.rhv.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private ErrorCode errorCode;

    public Response(String message){
        this.message = message;
        this.errorCode = ErrorCode.DEFAULT;
    }
}
